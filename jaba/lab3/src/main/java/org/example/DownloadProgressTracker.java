package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

public class DownloadProgressTracker {
    private boolean isLoadingFinished;
    private int amountOfParts;

    private BitSet parts;
    private String downloadingFileName;

    DownloadProgressTracker(int amountOfParts){
        isLoadingFinished = false;
        this.amountOfParts = amountOfParts;
        //System.out.println("amount of parts: " + amountOfParts);
        parts = new BitSet(amountOfParts);
    }

    public void FillingBitSet(TorrentParser torrentParser){
        ArrayList<byte[]> hashes = torrentParser.GetHashes();
        for(int i = 0; i < amountOfParts; i++){
            try {
                if (Arrays.equals(hashes.get(i), GetHash(i, torrentParser.GetPieceLen()))) {
                    parts.set(i);
                }
            } catch (IOException e){
                System.err.println("Exception in FillingBitSet: " + e.getMessage());
            }
        }
        if(parts.cardinality() == amountOfParts){
            System.out.println("File downloaded");
            isLoadingFinished = true;
        }
    }

    private byte[] GetHash(int pieceIndex, int pieceLen) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(downloadingFileName), "r");
        randomAccessFile.seek((long) pieceIndex * pieceLen);
        byte[] piece = new byte[pieceLen];
        int readBytes = randomAccessFile.read(piece);
        if(readBytes < 0) return new byte[20];
        byte[] hash = null;
        piece = Arrays.copyOfRange(piece, 0, readBytes);
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            hash = messageDigest.digest(piece);
        } catch (NoSuchAlgorithmException e){
            System.err.println("Exception in GetHash: " + e.getMessage());
        }
        return hash;
    }

    public byte[] GetPiece(int pieceIndex, int pieceLen) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(downloadingFileName), "r");
        randomAccessFile.seek((long) pieceIndex * pieceLen);
        byte[] piece = new byte[pieceLen];
        int readBytes = randomAccessFile.read(piece);
        piece = Arrays.copyOfRange(piece, 0, readBytes);
        return piece;
    }

    public boolean IsHashesEquals(Message message, TorrentParser torrentParser){
        byte[] hash1 = null;
        byte[] hash2 = torrentParser.GetHashOfPiece(message.getRequiredPieceIndex());
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            hash1 = messageDigest.digest(message.getPiece());
        } catch (NoSuchAlgorithmException e){
            System.err.println("Exception in GetHash: " + e.getMessage());
        }
        return Arrays.equals(hash1, hash2);
    }

    public void InsertPiece(byte[] piece, int pieceIndex, int pieceLen) throws IOException {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(new File(downloadingFileName), "rw")) {
            randomAccessFile.seek((long) pieceIndex * pieceLen);
            randomAccessFile.write(piece);
        } catch (IOException e) {
            System.out.println("error in writing piece to file");
        }

    }

    public BitSet getParts(){
        return parts;
    }

    public boolean isLoadingFinished() {
        return isLoadingFinished;
    }

    public void setDownloadingFileName(String downloadingFileName){
        this.downloadingFileName = downloadingFileName;
    }

    public void setBitInParts(int i){
        parts.set(i);
        if(parts.cardinality() == amountOfParts) isLoadingFinished = true;
    }
}
