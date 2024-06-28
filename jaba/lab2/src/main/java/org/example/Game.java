package org.example;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.*;
import java.io.File;


public class Game {

    public enum WinCondition {
        WIN,
        LOOSE,
        PLAYING,
    }

    private Field gamefield;
    private final int width = 988;
    private final int height = 988;
    private Player myPlayer;
    private ArrayList<Coin> coins;
    private ArrayList<Enemy> enemies;
    private Timer enemyTimer;
    public WinCondition winCondition;
    public int curLevel;
    private long startTime;
    private int lives;

    public void StartGame(File level) {
        curLevel = 1;
        gamefield = new Field(width, height, this);
        myPlayer = new Player(521, 781, 50);
        lives = 3;
        coins = new ArrayList<>();
        enemies = new ArrayList<>();
        gamefield.LoadingMap(level);
        try {
            LoadingCoins(new File(getClass().getClassLoader().getResource("coins.txt").toURI()));
        } catch (URISyntaxException e){
            System.err.println("error in loading coins1");
        }
        enemies.add(new Enemy(880, 105, 50));
        winCondition = WinCondition.PLAYING;
        startTime = System.currentTimeMillis();
    }

    public void restart(int level)  {
        curLevel = level;
        if(level == 2){
            myPlayer.Replace(521, 781);
            enemies.clear();
            try {
                gamefield.LoadingMap(new File(getClass().getClassLoader().getResource("map2.txt").toURI()));
            } catch (URISyntaxException e){
                System.err.println("error in loading map2");
            }
            try {
                LoadingCoins(new File(getClass().getClassLoader().getResource("coins2.txt").toURI()));
            } catch (URISyntaxException e){
                System.err.println("error in loading coins2");
            }
            enemies.add(new Enemy(880, 105, 50));
            winCondition = WinCondition.PLAYING;
        }
        startTime = System.currentTimeMillis();
    }

    public void restartLife(){
        myPlayer.Replace(521, 781);
        enemies.clear();
        enemies.add(new Enemy(880, 105, 50));
        winCondition = WinCondition.PLAYING;
        startTime = System.currentTimeMillis();
    }

    public void AddEnemies(int X, int Y, int s){
        enemies.add(new Enemy(X, Y, s));
    }

    public Player GetPlayer() {
        return myPlayer;
    }

    public Field GetField() {
        return gamefield;
    }

    public ArrayList<Coin> GetCoins() {
        return coins;
    }

    public ArrayList<Enemy> GetEnemies() {
        return enemies;
    }

    public int GetNumberOfLives(){ return lives; }

    public void MinusLive(){ lives--; }

    public float getTime(){
        return (float) (System.currentTimeMillis() - startTime) / 1000;
    }

    public void LoadingCoins(File file) {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                coins.add(new Coin(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
            }
        } catch (FileNotFoundException e) {
            System.err.println("error in loading file with map");
        }
    }

    public int GetAmountOfCoins() {
        return coins.size();
    }

    private int lastNumberOfDirections = 0;

    public void EnemyMoving() {
        for (Enemy enemy : enemies) {
            boolean isAllowedMovingDown = true;
            boolean isAllowedMovingUp = true;
            boolean isAllowedMovingRight = true;
            boolean isAllowedMovingLeft = true;
            ArrayList<Player.Direction> allowedDirections = new ArrayList<>();

            for (int i = enemy.GetPosY(); i < enemy.GetPosY() + enemy.GetSize(); i++) {
                for (int j = enemy.posX - 1; j > enemy.posX - 1 - enemy.GetSpeed(); j--) {
                    if (gamefield.GetFieldMap()[i][j] == 0) {
                        isAllowedMovingLeft = false;
                        break;
                    }
                }
            }
            if (isAllowedMovingLeft) {
                allowedDirections.add(Player.Direction.LEFT);
            }

            for (int i = enemy.GetPosY(); i < enemy.GetPosY() + enemy.GetSize(); i++) {
                for (int j = enemy.posX + enemy.GetSize(); j < enemy.posX + enemy.GetSize() + enemy.GetSpeed(); j++) {
                    if (gamefield.GetFieldMap()[i][j] == 0) {
                        isAllowedMovingRight = false;
                        break;
                    }
                }
            }
            if (isAllowedMovingRight) {
                allowedDirections.add(Player.Direction.RIGHT);
            }

            for (int i = enemy.GetPosY() + enemy.GetSize(); i < enemy.GetPosY() + enemy.GetSize() + enemy.GetSpeed(); i++) {
                for (int j = enemy.posX; j < enemy.posX + enemy.GetSize(); j++) {
                    if (gamefield.GetFieldMap()[i][j] == 0) {
                        isAllowedMovingDown = false;
                        break;
                    }
                }
            }
            if (isAllowedMovingDown) {
                allowedDirections.add(Player.Direction.DOWN);
            }

            for (int i = enemy.GetPosY() - 1; i > enemy.GetPosY() - 1 - enemy.GetSpeed(); i--) {
                for (int j = enemy.posX; j < enemy.posX + enemy.GetSize(); j++) {
                    if (gamefield.GetFieldMap()[i][j] == 0) {
                        isAllowedMovingUp = false;
                        break;
                    }
                }
            }
            if (isAllowedMovingUp) {
                allowedDirections.add(Player.Direction.UP);
            }

            int flag = 0;

            if (!allowedDirections.isEmpty()) {
                if (lastNumberOfDirections != allowedDirections.size() || !allowedDirections.contains(enemy.currentDir)) {
                    if (allowedDirections.size() >= 2) {
                        if (enemy.previousEnemyDir == Player.Direction.LEFT)
                            allowedDirections.remove(Player.Direction.RIGHT);
                        if (enemy.previousEnemyDir == Player.Direction.RIGHT)
                            allowedDirections.remove(Player.Direction.LEFT);
                        if (enemy.previousEnemyDir == Player.Direction.UP)
                            allowedDirections.remove(Player.Direction.DOWN);
                        if (enemy.previousEnemyDir == Player.Direction.DOWN)
                            allowedDirections.remove(Player.Direction.UP);
                        flag = 1;
                    }

                    Player.Direction preferredDirection = null;
                    int shortestDistance = Integer.MAX_VALUE;

                    for (Player.Direction direction : allowedDirections) {
                        int distance = Integer.MAX_VALUE;

                        switch (direction) {
                            case LEFT:
                                distance = Math.abs((enemy.GetPosX() - enemy.GetSpeed()) - myPlayer.GetPosX()) + Math.abs(enemy.GetPosY() - myPlayer.GetPosY());
                                break;
                            case RIGHT:
                                distance = Math.abs((enemy.GetPosX() + enemy.GetSpeed()) - myPlayer.GetPosX()) + Math.abs(enemy.GetPosY() - myPlayer.GetPosY());
                                break;
                            case UP:
                                distance = Math.abs(enemy.GetPosX() - myPlayer.GetPosX()) + Math.abs((enemy.GetPosY() - enemy.GetSpeed()) - myPlayer.GetPosY());
                                break;
                            case DOWN:
                                distance = Math.abs(enemy.GetPosX() - myPlayer.GetPosX()) + Math.abs((enemy.GetPosY() + enemy.GetSpeed()) - myPlayer.GetPosY());
                                break;
                        }

                        if (distance < shortestDistance) {
                            shortestDistance = distance;
                            preferredDirection = direction;
                        }
                    }

                    if (preferredDirection != null) {
                        enemy.currentDir = preferredDirection;
                    } else {
                        Random random = new Random(System.currentTimeMillis());
                        int randomIndex = random.nextInt(allowedDirections.size());
                        enemy.currentDir = allowedDirections.get(randomIndex);
                    }
                }

                switch (enemy.currentDir) {
                    case UP:
                        enemy.SetPosY(-enemy.GetSpeed());
                        enemy.previousEnemyDir = enemy.currentDir;
                        break;
                    case DOWN:
                        enemy.SetPosY(enemy.GetSpeed());
                        enemy.previousEnemyDir = enemy.currentDir;
                        break;
                    case RIGHT:
                        enemy.SetPosX(enemy.GetSpeed());
                        enemy.previousEnemyDir = enemy.currentDir;
                        break;
                    case LEFT:
                        enemy.SetPosX(-enemy.GetSpeed());
                        enemy.previousEnemyDir = enemy.currentDir;
                        break;
                }

                if (flag == 1) {
                    lastNumberOfDirections = allowedDirections.size() + 1;
                } else {
                    lastNumberOfDirections = allowedDirections.size();
                }
            }
        }
    }


    private void move(Player.Direction direction){
        switch (direction){
            case LEFT -> {
                myPlayer.SetPosX(-myPlayer.GetSpeed());
            }
            case RIGHT -> {
                myPlayer.SetPosX(myPlayer.GetSpeed());
            }
            case UP -> {
                myPlayer.SetPosY(-myPlayer.GetSpeed());
            }
            case DOWN -> {
                myPlayer.SetPosY(myPlayer.GetSpeed());
            }
        }
    }

    public void Moving(Player.Direction direction) {

        switch (direction) {
            case LEFT: {
                if (gamefield.isAllowedMoving(Player.Direction.LEFT)) {
                    move(Player.Direction.LEFT);
                    myPlayer.SetDirection(Player.Direction.LEFT);
                } else {
                    if (distance() < myPlayer.GetSpeed()) {
                        if(myPlayer.GetOldDirection() == Player.Direction.DOWN){
                            myPlayer.SetPosY(distance());
                        } else {
                            myPlayer.SetPosY(-distance());
                        }
                        myPlayer.SetDirection(Player.Direction.LEFT);
                    } else {
                        move(myPlayer.GetOldDirection());
                    }
                }
                break;
            }
            case RIGHT: {
                if (gamefield.isAllowedMoving(Player.Direction.RIGHT)) {
                    move(Player.Direction.RIGHT);
                    myPlayer.SetDirection(Player.Direction.RIGHT);
                } else {
                    if (distance() < myPlayer.GetSpeed()){
                        if(myPlayer.GetOldDirection() == Player.Direction.DOWN){
                            myPlayer.SetPosY(distance());
                        } else {
                            myPlayer.SetPosY(-distance());
                        }
                        myPlayer.SetDirection(Player.Direction.RIGHT);
                    } else move(myPlayer.GetOldDirection());
                }
                break;
            }
            case UP: {
                if (gamefield.isAllowedMoving(Player.Direction.UP)) {
                    move(Player.Direction.UP);
                    myPlayer.SetDirection(Player.Direction.UP);
                } else {
                    if (distance() < myPlayer.GetSpeed()) {
                        if(myPlayer.GetOldDirection() == Player.Direction.RIGHT){
                            myPlayer.SetPosX(distance());
                        } else {
                            myPlayer.SetPosX(-distance());
                        }
                        myPlayer.SetDirection(Player.Direction.UP);
                    } else move(myPlayer.GetOldDirection());
                }
                break;
            }
            case DOWN: {
                if (gamefield.isAllowedMoving(Player.Direction.DOWN)) {
                    move(Player.Direction.DOWN);
                    myPlayer.SetDirection(Player.Direction.DOWN);
                } else {
                    if (distance() < myPlayer.GetSpeed()){
                        if(myPlayer.GetOldDirection() == Player.Direction.RIGHT){
                            myPlayer.SetPosX(distance());
                        } else {
                            myPlayer.SetPosX(-distance());
                        }
                        myPlayer.SetDirection(Player.Direction.DOWN);
                    } else move(myPlayer.GetOldDirection());
                }
                break;
            }
        }
        gamefield.ChangingField();
        coins.removeIf(coin -> checkCollisions(myPlayer, coin));
    }

    public boolean checkCollisions(GameObject obj1, GameObject obj2) {
        if (obj1.GetPosY() + obj1.GetSize() <= obj2.GetPosY()) return false;
        if (obj1.GetPosY() >= obj2.GetPosY() + obj2.GetSize()) return false;
        if (obj1.GetPosX() + obj1.GetSize() <= obj2.GetPosX()) return false;
        if (obj1.GetPosX() >= obj2.GetPosX() + obj2.GetSize()) return false;
        return true;
    }

    public int distance() {
        switch (myPlayer.GetOldDirection()){
            case LEFT: {
                for (int i = 0; i < myPlayer.GetSize(); i++) {
                    if (gamefield.GetVerticalNet()[myPlayer.GetPosY()][myPlayer.GetPosX() - i] == 0) {
                        return i - 1;//если текущее влево
                    }
                }
                break;
            }
            case RIGHT: {
                for (int i = 0; i < myPlayer.GetSize(); i++) {
                    if (gamefield.GetVerticalNet()[myPlayer.GetPosY()][myPlayer.GetPosX() + i] == 0) {
                        return i + 2;//если текущее вправо
                    }
                }
                break;
            }
            case UP: {
                for (int i = 0; i < myPlayer.GetSize(); i++) {
                    if (gamefield.GetHorizontalNet()[myPlayer.GetPosY() - i][myPlayer.GetPosX()] == 0) {
                        return i - 1;
                    }
                }
                break;
            }
            case DOWN: {
                for (int i = 0; i < myPlayer.GetSize(); i++) {
                    if (gamefield.GetHorizontalNet()[myPlayer.GetPosY() + i][myPlayer.GetPosX()] == 0) {
                        return i + 2;
                    }
                }
                break;
            }
        }

        return 0;
    }

}
