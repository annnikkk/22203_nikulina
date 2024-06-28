package org.example;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements KeyListener {

    private Game myGame;
    private Timer timer;
    private Painting painting;

    private boolean leftButton = false;
    private boolean rightButton = false;
    private boolean upButton = false;
    private boolean downButton = false;

    public Controller(Game game, Painting painting) {
        myGame = game;
        this.painting = painting;
        GameTimer();

    }

    public void GameTimer(){
        this.timer = new java.util.Timer(); // swing timer
        timer.scheduleAtFixedRate(new TimerTask() {
            long start = System.currentTimeMillis();
            @Override
            public void run () {

                if (leftButton) myGame.Moving(Player.Direction.LEFT);
                else if (rightButton) myGame.Moving(Player.Direction.RIGHT);
                else if (upButton) myGame.Moving(Player.Direction.UP);
                else if (downButton) myGame.Moving(Player.Direction.DOWN);

                if (System.currentTimeMillis() - start >= 5000 && myGame.GetEnemies().size() < 2) myGame.AddEnemies(880, 105, 50);

                myGame.EnemyMoving();

                for (Enemy enemy : myGame.GetEnemies()) {
                    if (myGame.checkCollisions(myGame.GetPlayer(), enemy)) myGame.winCondition = Game.WinCondition.LOOSE;
                }

                if(myGame.GetCoins().isEmpty()) myGame.winCondition = Game.WinCondition.WIN;
                painting.repaint();
                int lastlevel = myGame.curLevel;
                if(myGame.winCondition != Game.WinCondition.PLAYING) {
                    painting.showGameStatus(myGame.winCondition);
                    restartTimer();
                }
                if (lastlevel != myGame.curLevel) restartTimer();
            }
        },5,5);
    }

    public void restartTimer() {
        timer.cancel();
        GameTimer();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                leftButton = true;
                //myGame.Moving(Player.Direction.LEFT); // Движение влево
                break;
            case KeyEvent.VK_RIGHT:
                rightButton = true;
                //myGame.Moving(Player.Direction.RIGHT); // Движение вправо
                break;
            case KeyEvent.VK_UP:
                upButton = true;
                //myGame.Moving(Player.Direction.UP); // Движение вверх
                break;
            case KeyEvent.VK_DOWN:
                downButton = true;
                //myGame.Moving(Player.Direction.DOWN); // Движение вниз
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                leftButton = false;
                //myGame.Moving(Player.Direction.LEFT); // Движение влево
                break;
            case KeyEvent.VK_RIGHT:
                rightButton = false;
                //myGame.Moving(Player.Direction.RIGHT); // Движение вправо
                break;
            case KeyEvent.VK_UP:
                upButton = false;
                //myGame.Moving(Player.Direction.UP); // Движение вверх
                break;
            case KeyEvent.VK_DOWN:
                downButton = false;
                //myGame.Moving(Player.Direction.DOWN); // Движение вниз
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
