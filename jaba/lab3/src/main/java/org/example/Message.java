package org.example;

import java.util.BitSet;

public class Message {
    public enum types{
        HANDSHAKE,
        PARTS,
        PIECE
    }

    private types type;
    private BitSet parts = null;
    private byte[] piece = null;
    private int requiredPieceIndex = -1;

    public Message(types type){
        this.type = type;
    }

    public types getType() {
        return type;
    }

    public BitSet getParts() {
        return parts;
    }

    public int getRequiredPieceIndex(){
        return requiredPieceIndex;
    }

    public byte[] getPiece() {
        return piece;
    }

    public void setParts(BitSet parts){
        this.parts = parts;
    }

    public void setPiece(byte[] piece){
        this.piece = piece;
    }

    public void setRequiredPieceIndex(int requiredPieceIndex){
        this.requiredPieceIndex = requiredPieceIndex;
    }


}
