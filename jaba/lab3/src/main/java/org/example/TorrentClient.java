package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.*;


public class TorrentClient implements Runnable {
    private DownloadProgressTracker progressTracker;
    private TorrentParser torrentParser;

    private ArrayList<ServerInfo> serverInfos;

    private Selector selector;
    private SocketHandler socketHandler;
    private Gson gson;

    TorrentClient(ArrayList<Peer> Peers, TorrentParser torrentParser, DownloadProgressTracker progressTracker) {
        this.torrentParser = torrentParser;
        this.progressTracker = progressTracker;

        serverInfos = new ArrayList<>();
        for (int i = 0; i < Peers.size(); i++) {
            serverInfos.add(new ServerInfo(Peers.get(i).GetIp(), Peers.get(i).GetPort()));
        }

        socketHandler = new SocketHandler();
        gson = new GsonBuilder().setLenient().create();
    }

    private void Registration() throws IOException {
        for (int i = 0; i < serverInfos.size(); i++) {
            if (serverInfos.get(i).GetConnectionFlag() != ServerInfo.ConnectionFlag.notConnected) continue;
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            try {
                socketChannel.connect(new InetSocketAddress(serverInfos.get(i).GetIp(), serverInfos.get(i).GetPort()));
            } catch (IOException e){
                System.out.println("server " + serverInfos.get(i).GetPort() + " unavailable");
                return;
            }
            socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE, serverInfos.get(i));
            serverInfos.get(i).SetSocketChannel(socketChannel);
        }
    }

    private void SendHandshake(ServerInfo serverInfo) throws IOException {
        Message message = new Message(Message.types.HANDSHAKE);
        String str = gson.toJson(message);
        socketHandler.WriteToSocket(serverInfo.GetSocketChannel(), str);
        serverInfo.SetConnectionFlag(ServerInfo.ConnectionFlag.requestHandshake);
        System.out.println("Client send handshake to server " + serverInfo.GetPort());
    }

    private void ReceiveHandshake(ServerInfo serverInfo) throws IOException {
        System.out.println("Client start receiving handshake");
        String str = socketHandler.ReadMessageFromSocket(serverInfo.GetSocketChannel());
        Message message;
        try{
            message = gson.fromJson(str, Message.class);
        } catch (JsonSyntaxException e){
            System.out.println(e.getMessage());
            return;
        }
        if (message.getType() == Message.types.HANDSHAKE)
            serverInfo.SetConnectionFlag(ServerInfo.ConnectionFlag.handshacked);
        System.out.println("Client receive handshake from server " + serverInfo.GetPort());
    }

    private void RequestParts(ServerInfo serverInfo) throws IOException {
        Message message = new Message(Message.types.PARTS);
        String str = gson.toJson(message);
        socketHandler.WriteToSocket(serverInfo.GetSocketChannel(), str);
        serverInfo.SetConnectionFlag(ServerInfo.ConnectionFlag.requestParts);
        System.out.println("Client send parts request to server " + serverInfo.GetPort());
    }

    private void ReceiveParts(ServerInfo serverInfo) throws IOException {
        String str = socketHandler.ReadMessageFromSocket(serverInfo.GetSocketChannel());
        Message message;
        try{
            message = gson.fromJson(str, Message.class);
        } catch (JsonSyntaxException e){
            System.out.println(e.getMessage());
            return;
        }
        if (message.getType() == Message.types.PARTS) {
            serverInfo.SetParts(message.getParts());
            serverInfo.SetConnectionFlag(ServerInfo.ConnectionFlag.receiveParts);
            System.out.println("Client receive parts from server " + serverInfo.GetPort());
        }
    }

    private void RequestPiece(ServerInfo serverInfo) throws IOException {
        Message message = new Message(Message.types.PIECE);
        int requiredPieceIndex = findFirstMissingPiece(progressTracker.getParts(), serverInfo.GetParts());
        if (requiredPieceIndex == -1) return;
        message.setRequiredPieceIndex(requiredPieceIndex);
        String str = gson.toJson(message);
        socketHandler.WriteToSocket(serverInfo.GetSocketChannel(), str);
        serverInfo.SetConnectionFlag(ServerInfo.ConnectionFlag.requestPiece);
        System.out.println("Client send piece " + requiredPieceIndex + " request to server " + serverInfo.GetPort());

    }

    private void ReceivePiece(ServerInfo serverInfo) throws IOException {
        String str = socketHandler.ReadMessageFromSocket(serverInfo.GetSocketChannel());
        Message message;
        try{
            message = gson.fromJson(str, Message.class);
        } catch (JsonSyntaxException e){
            System.out.println(e.getMessage());
            return;
        }
        if (message.getType() == Message.types.PIECE) {
            //надо сравнить хеш полученных данных и хэш из массива хешей
            if(!progressTracker.IsHashesEquals(message, torrentParser)){
                System.out.println("hashes not equals!!!");
                return;
            }
            serverInfo.SetConnectionFlag(ServerInfo.ConnectionFlag.receivePiece);
            progressTracker.InsertPiece(message.getPiece(), message.getRequiredPieceIndex(), torrentParser.GetPieceLen());
            progressTracker.setBitInParts(message.getRequiredPieceIndex());
            System.out.println("Received " + message.getRequiredPieceIndex() + " piece");
        }
    }

    private void ReceiveMessage(ServerInfo curServerInfo) throws IOException {
        switch (curServerInfo.GetConnectionFlag()) {
            case requestHandshake -> ReceiveHandshake(curServerInfo);
            case requestParts -> ReceiveParts(curServerInfo);
            case requestPiece -> ReceivePiece(curServerInfo);
            default -> {
                break;
            }
        }
    }

    private void SendMessage(ServerInfo curServerInfo) throws IOException {
        switch (curServerInfo.GetConnectionFlag()) {
            case connected -> SendHandshake(curServerInfo);
            case handshacked -> RequestParts(curServerInfo);
            case receiveParts -> RequestPiece(curServerInfo);
            case receivePiece -> RequestPiece(curServerInfo);
            default -> {
                break;
            }
        }
    }

    public int findFirstMissingPiece(BitSet myBitSet, BitSet serverBitSet) {
        for (int i = 0; i < torrentParser.GetNumberOfPieces(); i++) {
            if (!myBitSet.get(i) && !myBitSet.get(i) && serverBitSet.get(i)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void run() {
        //downloadingFile = new File(torrentParser.GetFileName());

        try {
            (new File(torrentParser.GetFileName())).createNewFile();//если уже создан, то второй раз не создастся
            progressTracker.setDownloadingFileName(torrentParser.GetFileName());
        } catch (IOException e) {
            System.err.println("Error in creating new file: " + e.getMessage());
        }

        progressTracker.FillingBitSet(torrentParser);

        try {
            selector = Selector.open();
        } catch (IOException e) {
            System.err.println("Error in opening selector: " + e.getMessage());
        }
        while (!progressTracker.isLoadingFinished()) {
            System.err.println("Client working");
            try {
                Registration();
            } catch (IOException e) {
                System.err.println("Error in registration: " + e.getMessage());
            }

            try {
                selector.select();
            } catch (IOException e) {
                System.err.println("Error in select: " + e.getMessage());
            }

            Set<SelectionKey> selectionKeySet = selector.selectedKeys();//какие каналы готовы для ввода-вывода
            Iterator<SelectionKey> iter = selectionKeySet.iterator();

            while (iter.hasNext()) {
                SelectionKey curKey = iter.next();
                ServerInfo curServerInfo = (ServerInfo) curKey.attachment();
                iter.remove();

                if(!curKey.isValid()) {
                    curServerInfo.SetConnectionFlag(ServerInfo.ConnectionFlag.notConnected);
                    continue;
                }

                //System.out.println("Trying connecting to server: " + curServerInfo.GetPort());
                if (curKey.isConnectable()) {

                    try{
                        //System.out.println("Trying finish connecting to server: " + curServerInfo.GetPort());
                        if (curServerInfo.GetSocketChannel().finishConnect()) {
                            System.out.println("Finish connecting to server: " + curServerInfo.GetPort());
                            curServerInfo.SetConnectionFlag(ServerInfo.ConnectionFlag.connected);
                        }
                    } catch(IOException e) {
                        curKey.cancel();
                        try{
                            curKey.channel().close();
                        } catch (IOException exception){
                            System.err.println(exception.getMessage());
                        }
                        curServerInfo.SetConnectionFlag(ServerInfo.ConnectionFlag.notConnected);
                        continue;
                    }
                }

                if (curServerInfo.GetConnectionFlag() == ServerInfo.ConnectionFlag.notConnected) continue;

                if (curKey.isReadable()) {
                    try {
                        ReceiveMessage(curServerInfo);
                    } catch (IOException e){
                        curKey.cancel();
                        try{
                            curKey.channel().close();
                        } catch (IOException exception){
                            System.err.println(exception.getMessage());
                        }
                        curServerInfo.SetConnectionFlag(ServerInfo.ConnectionFlag.notConnected);
                        continue;
                    }
                }

                if (curKey.isWritable()) {
                    try{
                        SendMessage(curServerInfo);
                    } catch (IOException e){
                        curKey.cancel();
                        try{
                            curKey.channel().close();
                        } catch (IOException exception){
                            System.err.println(exception.getMessage());
                        }
                        curServerInfo.SetConnectionFlag(ServerInfo.ConnectionFlag.notConnected);
                        continue;
                    }
                }
            }
        }
        System.out.println("File downloaded");

    }

}
