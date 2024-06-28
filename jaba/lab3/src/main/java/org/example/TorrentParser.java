package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

import com.dampcake.bencode.*;

public class TorrentParser {
    private BencodeInputStream bencodeInputStream;
    private Map<String, Object> info;
    private ArrayList<byte[]> hashes;
    public TorrentParser(File torrentFile){
        try {
            bencodeInputStream = new BencodeInputStream(new FileInputStream(torrentFile), StandardCharsets.ISO_8859_1);
            hashes = new ArrayList<>();
            Map<String, Object> TorrentFileContent = bencodeInputStream.readDictionary();
            info = (Map<String, Object>) TorrentFileContent.get("info");
            //System.out.println(info);
        } catch (IOException e){
            System.err.println("err in torrent-file parsing");
        }
        FillingHashes();
    }

    private void FillingHashes(){
        final int HASHLEN = 20;
        String allHash = info.get("pieces").toString();
        int numberOfHashes = allHash.length() / HASHLEN;
        for(int i = 0; i < numberOfHashes; i++){
            String hash = allHash.substring(i*HASHLEN, i*HASHLEN+HASHLEN);
            hashes.add(hash.getBytes(StandardCharsets.ISO_8859_1));
        }
    }

    public byte[] GetHashOfPiece(int index){ return hashes.get(index); }

    public String GetFileName(){
        return info.get("name").toString();
    }

    int GetNumberOfPieces(){
        return hashes.size();
    }

    public ArrayList<byte[]> GetHashes(){
        return hashes;
    }

    int GetPieceLen(){
        return Integer.parseInt(info.get("piece length").toString());
    }
}
