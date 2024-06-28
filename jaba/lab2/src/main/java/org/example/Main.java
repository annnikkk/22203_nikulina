package org.example;
import javax.swing.SwingUtilities;
import java.io.File;
import java.net.URISyntaxException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game diggerGame = new Game();
                try {
                    diggerGame.StartGame(new File(getClass().getClassLoader().getResource("map.txt").toURI()));
                } catch (URISyntaxException e) {
                    System.err.println("error in loading map1");
                }
                diggerGame.GetField().InitialiseNets();
                Painting painting = new Painting(diggerGame);
                Controller fullControl = new Controller(diggerGame, painting);
                painting.setKeyListener(fullControl);
            }
        });
    }
}