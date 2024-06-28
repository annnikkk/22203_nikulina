package org.example;
/*
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Game1 {

    public enum WinCondition {
        WIN,
        LOOSE
    }

    private Field gamefield;
    private Player myPlayer;
    private ArrayList<Coin> coins;
    private ArrayList<Enemy> enemies;
    private long state;
    private Timer enemyTimer;
    public WinCondition winCondition;

    public void StartGame(String level) {
        gamefield = new Field(1000, 1000, this);
        myPlayer = new Player(521, 781, 50);
        coins = new ArrayList<>();
        enemies = new ArrayList<>();
        gamefield.LoadingMap(level);
        LoadingCoins(new File("/home/annnik/programming/java-labs/lab2/src/main/resources/coins.txt"));
        enemies.add(new Enemy(880, 105, 50));
        enemies.add(new Enemy(880, 105, 50));


        this.enemyTimer = new Timer();
        enemyTimer.scheduleAtFixedRate(new TimerTask() {
            long lastState;
            @Override
            public void run() {
                state++;
                for (Enemy enemy : enemies) {
                    if (checkCollisions(myPlayer, enemy)) winCondition = WinCondition.LOOSE;
                }
                EnemyMoving();
            }
        },5, 5);
    }

    public long GetState() {
        return state;
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
            ArrayList<Player.Direction> AllowedDirections = new ArrayList<>();

            for (int i = enemy.GetPosY(); i < enemy.GetPosY() + enemy.GetSize(); i++) {
                for (int j = enemy.posX - 1; j > enemy.posX - 1 - enemy.GetSpeed(); j--) {
                    if (gamefield.GetFieldMap()[i][j] == 0) {
                        isAllowedMovingLeft = false;

                        break;
                    }
                }
            }
            if (isAllowedMovingLeft) {
                AllowedDirections.add(Player.Direction.LEFT);
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
                AllowedDirections.add(Player.Direction.RIGHT);
            }
            for (int i = enemy.GetPosY() + enemy.GetSize() ; i < enemy.GetPosY() + enemy.GetSize()  + enemy.GetSpeed(); i++) {
                for (int j = enemy.posX; j < enemy.posX + enemy.GetSize(); j++) {
                    if (gamefield.GetFieldMap()[i][j] == 0) {
                        isAllowedMovingDown = false;
                        break;
                    }
                }
            }
            if (isAllowedMovingDown) {
                AllowedDirections.add(Player.Direction.DOWN);
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
                AllowedDirections.add(Player.Direction.UP);
            }
            int flag = 0;
            if (!AllowedDirections.isEmpty() ) {
                if((lastNumberOfDirections != AllowedDirections.size() || !AllowedDirections.contains(enemy.currentDir))){
                    if (AllowedDirections.size() >= 2){
                        if(enemy.previousEnemyDir == Player.Direction.LEFT) AllowedDirections.remove(Player.Direction.RIGHT);
                        if(enemy.previousEnemyDir == Player.Direction.RIGHT) AllowedDirections.remove(Player.Direction.LEFT);
                        if(enemy.previousEnemyDir == Player.Direction.UP) AllowedDirections.remove(Player.Direction.DOWN);
                        if(enemy.previousEnemyDir == Player.Direction.DOWN) AllowedDirections.remove(Player.Direction.UP);
                        flag = 1;
                    }
                    Random random = new Random(state);
                    int randomIndex = random.nextInt(AllowedDirections.size());
                    Player.Direction randomDirection = AllowedDirections.get(randomIndex);
                    enemy.currentDir = randomDirection;
                }

                switch (enemy.currentDir){
                    case UP :
                        enemy.SetPosY(-enemy.GetSpeed());
                        enemy.previousEnemyDir = enemy.currentDir;
                        break;
                    case DOWN :
                        enemy.SetPosY(enemy.GetSpeed());
                        enemy.previousEnemyDir = enemy.currentDir;
                        break;
                    case RIGHT :
                        enemy.SetPosX(enemy.GetSpeed());
                        enemy.previousEnemyDir = enemy.currentDir;
                        break;
                    case LEFT :
                        enemy.SetPosX(-enemy.GetSpeed());
                        enemy.previousEnemyDir = enemy.currentDir;
                        break;
                }
                if(flag == 1) {
                    lastNumberOfDirections = AllowedDirections.size() + 1;
                } else {
                    lastNumberOfDirections = AllowedDirections.size();
                }
                state++;
            }
        }
    }

    public void Moving(int X, int Y, Player.Direction direction) {
        if (direction != myPlayer.CurDirection()) {
            Turning(direction);
            //gamefield.ChangingField(X, Y, myPlayer.GetPosX(), myPlayer.GetPosY());
        } else {
            myPlayer.SetPosX(X);
            myPlayer.SetPosY(Y);
            gamefield.ChangingField(X, Y, myPlayer.GetPosX(), myPlayer.GetPosY());
        }
        coins.removeIf(coin -> checkCollisions(myPlayer, coin));
        state++;
    }

    private boolean checkCollisions(GameObject obj1, GameObject obj2) {
        if (obj1.GetPosY() + obj1.GetSize() <= obj2.GetPosY()) return false;
        if (obj1.GetPosY() >= obj2.GetPosY() + obj2.GetSize()) return false;
        if (obj1.GetPosX() + obj1.GetSize() <= obj2.GetPosX()) return false;
        if (obj1.GetPosX() >= obj2.GetPosX() + obj2.GetSize()) return false;
        return true;
    }

    public void Turning(Player.Direction direction) {
        if(direction == myPlayer.CurDirection()) return;
        int oldX = myPlayer.GetPosX();
        int oldY = myPlayer.GetPosY();
        if (direction == Player.Direction.LEFT) {
            if (myPlayer.CurDirection() == Player.Direction.DOWN) {
                for (int i = 0; i < myPlayer.GetSize(); i++) {
                    if (gamefield.GetHorizontalNet()[myPlayer.GetPosY() + i][myPlayer.GetPosX()] == 0) {
                        myPlayer.SetPosY(i + 2);
                    }
                }
                gamefield.ChangingFieldBeforeTurn(oldX, oldY, myPlayer.GetPosX(), myPlayer.GetPosY(), Player.Direction.DOWN);
            }
            if (myPlayer.CurDirection() == Player.Direction.UP) {
                for (int i = 0; i < myPlayer.GetSize(); i++) {
                    if (gamefield.GetHorizontalNet()[myPlayer.GetPosY() + i][myPlayer.GetPosX()] == 0) {
                        myPlayer.SetPosY(-myPlayer.GetSize() + i);
                    }
                }
                gamefield.ChangingFieldBeforeTurn(oldX, oldY, myPlayer.GetPosX(), myPlayer.GetPosY(), Player.Direction.UP);
            }
            myPlayer.SetDirection(Player.Direction.LEFT);
            if (myPlayer.CurDirection() == Player.Direction.RIGHT) {
                return;
            }
        }
        if (direction == Player.Direction.RIGHT) {
            if (myPlayer.CurDirection() == Player.Direction.DOWN) {
                for (int i = 0; i < myPlayer.GetSize(); i++) {
                    if (gamefield.GetHorizontalNet()[myPlayer.GetPosY() + i][myPlayer.GetPosX()] == 0) {
                        myPlayer.SetPosY(i + 2);
                    }
                }
                gamefield.ChangingFieldBeforeTurn(oldX, oldY, myPlayer.GetPosX(), myPlayer.GetPosY(), Player.Direction.DOWN);
            }
            if (myPlayer.CurDirection() == Player.Direction.UP) {
                for (int i = 0; i < myPlayer.GetSize(); i++) {
                    if (gamefield.GetHorizontalNet()[myPlayer.GetPosY() + i][myPlayer.GetPosX()] == 0) {
                        myPlayer.SetPosY(-myPlayer.GetSize() + i);
                    }
                }
                gamefield.ChangingFieldBeforeTurn(oldX, oldY, myPlayer.GetPosX(), myPlayer.GetPosY(), Player.Direction.UP);
            }
            myPlayer.SetDirection(Player.Direction.RIGHT);
            if (myPlayer.CurDirection() == Player.Direction.LEFT) {
                return;
            }
        }
        if (direction == Player.Direction.DOWN) {
            if (myPlayer.CurDirection() == Player.Direction.LEFT) {
                for (int i = 0; i < myPlayer.GetSize(); i++) {
                    if (gamefield.GetVerticalNet()[myPlayer.GetPosY()][myPlayer.GetPosX() + i] == 0) {
                        myPlayer.SetPosX(-myPlayer.GetSize() + i);//если текущее влево
                    }
                }
                gamefield.ChangingFieldBeforeTurn(oldX, oldY, myPlayer.GetPosX(), myPlayer.GetPosY(), Player.Direction.LEFT);

            }
            if (myPlayer.CurDirection() == Player.Direction.RIGHT) {
                for (int i = 0; i < myPlayer.GetSize(); i++) {
                    if (gamefield.GetVerticalNet()[myPlayer.GetPosY()][myPlayer.GetPosX() + i] == 0) {
                        myPlayer.SetPosX(i + 2);//если текущее вправо
                    }
                }
                gamefield.ChangingFieldBeforeTurn(oldX, oldY, myPlayer.GetPosX(), myPlayer.GetPosY(), Player.Direction.RIGHT);
            }
            myPlayer.SetDirection(Player.Direction.DOWN);
            if (myPlayer.CurDirection() == Player.Direction.UP) {
                return;
            }

        }
        if (direction == Player.Direction.UP) {
            if (myPlayer.CurDirection() == Player.Direction.LEFT) {
                for (int i = 0; i < myPlayer.GetSize(); i++) {
                    if (gamefield.GetVerticalNet()[myPlayer.GetPosY()][myPlayer.GetPosX() + i] == 0) {
                        myPlayer.SetPosX(-myPlayer.GetSize() + i);//если текущее влево
                    }
                }
                gamefield.ChangingFieldBeforeTurn(oldX, oldY, myPlayer.GetPosX(), myPlayer.GetPosY(), Player.Direction.LEFT);
            }
            if (myPlayer.CurDirection() == Player.Direction.RIGHT) {
                for (int i = 0; i < myPlayer.GetSize(); i++) {
                    if (gamefield.GetVerticalNet()[myPlayer.GetPosY()][myPlayer.GetPosX() + i] == 0) {
                        myPlayer.SetPosX(i + 2);//если текущее вправо
                    }
                }
                gamefield.ChangingFieldBeforeTurn(oldX, oldY, myPlayer.GetPosX(), myPlayer.GetPosY(), Player.Direction.RIGHT);
            }
            myPlayer.SetDirection(Player.Direction.UP);
            if (myPlayer.CurDirection() == Player.Direction.DOWN) {
                return;
            }
        }
    }
}
*/