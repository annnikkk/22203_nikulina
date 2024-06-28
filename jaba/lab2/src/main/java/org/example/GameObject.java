package org.example;

public class GameObject {
    protected int posX;
    protected int posY;
    protected int size;
    GameObject(int X, int Y, int s) {
        posX = X;
        posY = Y;
        size = s;
    }
    public int GetPosX() {
        return posX;
    }

    public int GetPosY() {
        return posY;
    }

    public int GetSize() {
        return size;
    }
}
