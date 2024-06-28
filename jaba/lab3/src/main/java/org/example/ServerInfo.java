package org.example;

import java.nio.channels.SocketChannel;
import java.util.BitSet;

public class ServerInfo {

    public enum ConnectionFlag{
        notConnected,
        connected,
        requestHandshake,
        handshacked,
        requestParts,
        receiveParts,
        requestPiece,
        receivePiece
    }

    ServerInfo(String IP, int PORT){
        ip = IP;
        port = PORT;
    }

    private String ip;
    private int port;
    private ConnectionFlag connectionFlag = ConnectionFlag.notConnected;
    private BitSet parts;
    private SocketChannel socketChannel;

    public ConnectionFlag GetConnectionFlag(){
        return connectionFlag;
    }

    public String GetIp(){
        return ip;
    }

    public int GetPort(){
        return port;
    }
    public SocketChannel GetSocketChannel(){
        return socketChannel;
    }

    public void SetSocketChannel(SocketChannel channel){
        socketChannel = channel;
    }

    public void SetConnectionFlag(ConnectionFlag flag){
        connectionFlag = flag;
    }

    public void SetParts(BitSet parts){
        this.parts = parts;
    }

    public BitSet GetParts(){
        return parts;
    }
}
