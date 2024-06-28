package org.example;

import com.google.gson.Gson;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Scanner;


public class Main {
    private ArrayList<Peer> peers;
    private TorrentClient myClient;
    private TorrentServer myServer;
    private TorrentParser torrentParser;
    private DownloadProgressTracker progressTracker;

    public Main() throws URISyntaxException {
        peers = new ArrayList<>();
        LoadingPeers("peers.txt");//тут настоящий путь
        torrentParser = new TorrentParser(new File(getClass().getClassLoader().getResource("file1.mstr.torrent").toURI()));
        //torrentParser = new TorrentParser(new File(getClass().getClassLoader().getResource("SMC-2.ftp.torrent").toURI()));
        progressTracker = new DownloadProgressTracker(torrentParser.GetNumberOfPieces());
        myClient = new TorrentClient(peers, torrentParser, progressTracker);//название файла
        myServer = new TorrentServer(8954, progressTracker, torrentParser);
    }

    public void StartThreads(){
        try {
            Thread ServerThread = new Thread(myServer);
            Thread ClientThread = new Thread(myClient);
            ServerThread.start();
            System.out.println("Server thread started");
            ClientThread.start();
            System.out.println("Client thread started");
            ServerThread.join();
            System.out.println("Server thread joined");
            ClientThread.join();
            System.out.println("Client thread joined");
        } catch (InterruptedException e){
            System.err.println("err in threads");
        }
    }

    public static void main(String[] args) {
        try {
            Main main = new Main();
            main.StartThreads();
        } catch (Exception e){
            System.err.println(e.getMessage());
        }

    }
    public void LoadingPeers (String path){
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
             Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    String ip = parts[0];
                    int port = Integer.parseInt(parts[1]);
                    peers.add(new Peer(ip, port));
                }
            }
        } catch (IOException e) {
            System.err.println("error in loading peers");
        }
    }
}