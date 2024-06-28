package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.Arrays;


public class Painting extends JPanel {
    private Game myGame;
    private JFrame frame;
    private Image heartImage = new ImageIcon(getClass().getClassLoader().getResource("heart.png")).getImage();


    public Painting(Game game) {
        myGame = game;

        setPreferredSize(new Dimension(1000, 1000));
        ImageIcon heartIcon = new ImageIcon(getClass().getClassLoader().getResource("heart.png"));
        heartImage = heartIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        // Создаем JFrame и настраиваем его
        frame = new JFrame("Digger");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); // Запрещаем изменение размеров окна пользователем

        // Добавляем JPanel в JFrame
        frame.add(this);

        // Устанавливаем размеры JFrame (не забываем про insets)
        frame.pack();

        // Устанавливаем окно по центру экрана
        frame.setLocationRelativeTo(null);

        // Делаем JFrame видимым
        frame.setVisible(true);
    }

    public void setKeyListener(KeyListener keyListener){
        frame.addKeyListener(keyListener);
    }

    public void showGameStatus(Game.WinCondition condition){
        if(condition == Game.WinCondition.LOOSE){
            myGame.MinusLive();
            if(myGame.GetNumberOfLives() > 0) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Thread was interrupted");
                }
                myGame.restartLife();
                return;
            }
            if (myGame.GetNumberOfLives() == 0) {
                JOptionPane.showMessageDialog(null, "You loose ha-ha-ha!");
                System.exit(0);
            }
        } else{
            if(myGame.curLevel < 2){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Thread was interrupted");
                    }
                myGame.restart(myGame.curLevel+1);
            } else {
                JOptionPane.showMessageDialog(null, "You win!");
                System.exit(0);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int pixSize = 1;
        for (int i = 0; i < myGame.GetField().GetFieldMap().length; i++) {
            for (int j = 0; j < myGame.GetField().GetFieldMap()[i].length; j++) {
                if (myGame.GetField().GetFieldMap()[i][j] == 0 || myGame.GetField().GetFieldMap()[i][j] == 3) { //пустое место
                    Color brown = new Color(195, 142, 112);
                    g.setColor(brown);
                    g.fillRect(j * pixSize, i * pixSize, pixSize, pixSize);
                } else if (myGame.GetField().GetFieldMap()[i][j] == 1) { //раскопки
                    Color darkbrown = new Color(31, 21, 21);
                    g.setColor(darkbrown);
                    g.fillRect(j * pixSize, i * pixSize, pixSize, pixSize);
                }
            }
        }

        g.setColor(Color.YELLOW);
        for (int i = 0; i < myGame.GetAmountOfCoins(); i++) {
                g.fillOval(myGame.GetCoins().get(i).GetPosX(), myGame.GetCoins().get(i).GetPosY(), myGame.GetCoins().get(i).GetSize(), myGame.GetCoins().get(i).GetSize());
        }

        Color pink = new Color(0xBB0870);
        g.setColor(pink);
        g.fillRect(myGame.GetPlayer().GetPosX(), myGame.GetPlayer().GetPosY(), myGame.GetPlayer().GetSize(), myGame.GetPlayer().GetSize());

        Color green = new Color(56, 162, 97);
        g.setColor(green);
        for (int i = 0; i < myGame.GetEnemies().size(); i++) {
            g.fillRect(myGame.GetEnemies().get(i).GetPosX(), myGame.GetEnemies().get(i).GetPosY(), myGame.GetEnemies().get(i).GetSize(),myGame.GetEnemies().get(i).GetSize());
        }

        for (int i = 0; i < myGame.GetNumberOfLives(); i++) {

            int xOffset = i * 40;
            g.drawImage(heartImage, xOffset, 0, this);
        }

    }

}
