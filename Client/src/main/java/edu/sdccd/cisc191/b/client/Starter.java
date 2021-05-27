package edu.sdccd.cisc191.b.client;

import javax.swing.JFrame;
import java.awt.*;

/**
 * Starter is a extended class of JFrame. Starter class will define the way game will look and it will also show title.
 */

public class Starter extends JFrame {

    GameView g = new GameView();

    /**
     * Generates the game view components.
     */
    public Starter()
    {
        Dimension size = Toolkit. getDefaultToolkit(). getScreenSize();
        add(g);
        setTitle("Battle X Armada");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize((int) (size.getWidth() / 2),(int) (size.getHeight() - 35));
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        setResizable(false);
    }

    /**
     * Launches the Starter class.
     *
     */
    public static void main(String[] args) {
        new Starter();
    }
}