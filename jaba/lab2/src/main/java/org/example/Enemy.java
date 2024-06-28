package org.example;

public class Enemy extends GameObject{

    private int speed;
    Enemy(int X, int Y, int s) {
        super(X, Y, s);
        speed = 1;
    }
    public int GetSpeed(){
        return speed;
    }
    public void SetPosX(int X){
        posX += X;
    }
    public void SetPosY(int Y){
        posY += Y;
    }
    public Player.Direction currentDir;
    public Player.Direction previousEnemyDir;
}
