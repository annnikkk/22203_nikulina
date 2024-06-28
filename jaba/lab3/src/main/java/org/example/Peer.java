package org.example;

public class Peer {
    private String ip;
    private int port;
    Peer(String IP, int PORT){
        ip = IP;
        port = PORT;
    }

    public String GetIp(){
        return ip;
    }
    public int GetPort(){
        return port;
    }
}
