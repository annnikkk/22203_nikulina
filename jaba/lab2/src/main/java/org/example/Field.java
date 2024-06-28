package org.example;

import java.io.*;
import java.util.Scanner;

public class Field {
    private static int height;
    private static int width;
    private int[][] map;
    private int[][] horizontalNet;
    private int[][] verticalNet;
    private Game myGame;

    public Field(int width, int height, Game game) {
        this.width = width;
        this.height = height;
        map = new int[width][height];
        horizontalNet = new int[width][height];
        verticalNet = new int[width][height];
        myGame = game;
    }

    public void InitialiseNets() {
        int indicator = 0;
        for (int i = 0; i < height; i++) {
            if (indicator == 1) {
                int k = 0;
                for (k = i; k < i + myGame.GetPlayer().GetSize(); k++) {
                    if (k >= width) {
                        System.out.println(k);
                        break;
                    }
                    for (int j = 0; j < width; j++) {
                        horizontalNet[k][j] = 1;//тоннель
                    }
                }
                i = k;
                indicator = 0;
            } else {
                for (int j = 0; j < width; j++) {
                    horizontalNet[i][j] = 0;//канал между тунеллями
                }
                indicator = 1;
            }
        }
        indicator = 0;
        for (int i = 0; i < width; i++) {
            if (indicator == 1) {
                int k = 0;
                for (k = i; k < i + myGame.GetPlayer().GetSize(); k++) {
                    if (k >= height) {
                        System.out.println(k);
                        break;
                    }
                    for (int j = 0; j < height; j++) {
                        verticalNet[j][k] = 1;//верт. тоннель
                    }
                }
                i = k;
                indicator = 0;
            } else {
                for (int j = 0; j < height; j++) {
                    verticalNet[j][i] = 0;//канал между верт. тоннелями
                }
                indicator = 1;
            }
        }
    }

    public void LoadingMap(File file) {
        try (Scanner scanner = new Scanner(file)) {
            int i = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                for (int j = 0; j < parts.length; j++) {
                    map[i][j] = Integer.parseInt(parts[j]);
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            System.err.println("error in loading file with map");
        }
    }

    public int[][] GetFieldMap() {
        return map;
    }

    public int[][] GetVerticalNet() {
        return verticalNet;
    }

    public int[][] GetHorizontalNet() {
        return horizontalNet;
    }

    public boolean isAllowedMoving(Player.Direction newDir){
        if(newDir == Player.Direction.LEFT || newDir == Player.Direction.RIGHT){
            for(int i = 0; i < myGame.GetPlayer().GetSize(); i++){
                if(myGame.GetField().GetHorizontalNet()[myGame.GetPlayer().GetPosY()+i][myGame.GetPlayer().GetPosX()] == 0) return false;
            }
        }
        if(newDir == Player.Direction.UP || newDir == Player.Direction.DOWN){
            for(int i = 0; i < myGame.GetPlayer().GetSize(); i++){
                if(myGame.GetField().GetVerticalNet()[myGame.GetPlayer().GetPosY()][myGame.GetPlayer().GetPosX()+i] == 0) return false;
            }
        }
        return true;
    }

    /*public void ChangingFieldBeforeTurn(int oldX, int oldY, int newX, int newY, Player.Direction oldDir) {
        if (oldDir == Player.Direction.LEFT) {
                if(newX-oldX >= myGame.GetPlayer().GetSpeed()){
                    myGame.Moving(-myGame.GetPlayer().GetSpeed(), 0, Player.Direction.LEFT);
                    oldX += myGame.GetPlayer().GetSpeed();
                    myGame.GetPlayer().turning = true;
                } else {
                    for (int j = oldX + myGame.GetPlayer().GetSize(); j >= newX + myGame.GetPlayer().GetSize(); j--) {
                        for (int i = newY; i < newY + myGame.GetPlayer().GetSize(); i++) {
                            map[i][j] = 1;
                        }
                    }
                    myGame.GetPlayer().turning = false;
                }
           // for (int j = oldX + myGame.GetPlayer().GetSize(); j >= newX + myGame.GetPlayer().GetSize(); j--) {
           //     for (int i = newY; i < newY + myGame.GetPlayer().GetSize(); i++) {
           //         map[i][j] = 1;
           //     }
           //}
        }
        if (oldDir == Player.Direction.RIGHT) {
            for (int j = oldX; j <= newX; j++) {
                for (int i = newY; i < newY + myGame.GetPlayer().GetSize(); i++) {
                    map[i][j] = 1;
                }
            }
        }
        if (oldDir == Player.Direction.UP) {
            for (int i = newY + myGame.GetPlayer().GetSize(); i <= oldY + myGame.GetPlayer().GetSize(); i++) {
                for (int j = newX; j < newX + myGame.GetPlayer().GetSize(); j++) {
                    map[i][j] = 1;
                }
            }
        }
        if (oldDir == Player.Direction.DOWN) {
            for (int i = oldY; i <= newY; i++) {
                for (int j = newX; j < newX + myGame.GetPlayer().GetSize(); j++) {
                    map[i][j] = 1;
                }
            }
        }
    }*/

    public void ChangingField(){
        for(int x = myGame.GetPlayer().GetPosX(); x < myGame.GetPlayer().GetPosX() + myGame.GetPlayer().GetSize();x++){
            for(int y = myGame.GetPlayer().GetPosY(); y < myGame.GetPlayer().GetPosY() + myGame.GetPlayer().GetSize(); y++){
                map[y][x] = 1;
            }
        }
    }

    public void writeMapToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    writer.write(Integer.toString(map[i][j]));
                    if (j < map[i].length - 1) {
                        writer.write(" ");
                    }
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int GetWidth(){
        return width;
    }

    public static int GetHeight(){
        return height;
    }
}
