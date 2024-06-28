package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TorrentServer implements Runnable {
    private int port;
    private SocketHandler socketHandler;
    private DownloadProgressTracker progressTracker;
    private Selector selector;
    private Gson gson;
    private TorrentParser torrentParser;

    public TorrentServer(int port, DownloadProgressTracker progressTracker, TorrentParser torrentParser) {
        this.port = port;
        this.progressTracker = progressTracker;
        this.torrentParser = torrentParser;
        socketHandler = new SocketHandler();
        gson = new GsonBuilder().setLenient().create();
    }

    private void ProcessingRequest(SocketChannel socketChannel) throws IOException {
        String str = socketHandler.ReadMessageFromSocket(socketChannel);
        Message message = gson.fromJson(str, Message.class);
        if (message == null) return;
        System.out.println("Processing message with type " + message.getType());
        switch (message.getType()) {
            case HANDSHAKE -> {
                socketHandler.WriteToSocket(socketChannel, str);
                System.out.println("Server " + port + " sent handshake to " + socketChannel.socket().getPort());
            }
            case PARTS -> {
                message.setParts(progressTracker.getParts());
                str = gson.toJson(message);
                socketHandler.WriteToSocket(socketChannel, str);
            }
            case PIECE -> {
                message.setPiece(progressTracker.GetPiece(message.getRequiredPieceIndex(), torrentParser.GetPieceLen()));
                str = gson.toJson(message);
                socketHandler.WriteToSocket(socketChannel, str);
                System.out.println("Sent " + message.getRequiredPieceIndex() + " piece");
            }
        }
    }

    @Override
    public void run() {
        try {
            selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        while (true) {
            System.out.println("Start sitting on select");
            try {
                selector.select();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectionKeySet.iterator();

            while (iter.hasNext()) {
                SelectionKey curKey = iter.next();
                iter.remove();

                if (!curKey.isValid()) continue;

                if (curKey.isAcceptable()) {
                    System.out.println("Accepting");
                    try {
                        SocketChannel socketChannel = ((ServerSocketChannel) curKey.channel()).accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } catch (IOException e) {
                        System.err.println("Error accepting connection: " + e.getMessage());
                        try{
                            curKey.channel().close();
                        } catch (IOException exception){
                            System.err.println(exception.getMessage());
                        }
                    }
                }

                if (curKey.isReadable()) {
                    System.out.println("Reading");
                    SocketChannel socketChannel = (SocketChannel) curKey.channel();
                    try {
                        ProcessingRequest(socketChannel);
                    } catch (IOException e) {
                        System.err.println("Error reading from socket: " + e.getMessage());
                        curKey.cancel();
                        try{
                            curKey.channel().close();
                        } catch (IOException exception){
                            System.err.println(exception.getMessage());
                        }
                    }
                }
            }
        }
    }
}
