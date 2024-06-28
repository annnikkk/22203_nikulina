package org.example;

public class Player extends GameObject{

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        NODIRECTION
    }

    private int speed = 1;

    public Player(int X, int Y, int s){
        super(X, Y, s);
    }
    public int GetSpeed(){
        return speed;
    }

    private Direction oldDirection = Direction.NODIRECTION;

    public void SetDirection(Direction dir){
        oldDirection = dir;
    }

    public Direction GetOldDirection(){
        return oldDirection;
    }

    public void SetPosX(int X){
        posX += X;
        if(posX < 0) posX = 0;
        else if(posX + size >= Field.GetWidth()) posX = Field.GetWidth() - size - 1;
    }
    public void SetPosY(int Y){
        posY += Y;
        if(posY < 0) posY = 0;
        else if(posY + size >= Field.GetHeight()) posY = Field.GetHeight() - size - 1;
    }
    public void Replace(int X, int Y){
        posX = X;
        posY = Y;
    }
}
