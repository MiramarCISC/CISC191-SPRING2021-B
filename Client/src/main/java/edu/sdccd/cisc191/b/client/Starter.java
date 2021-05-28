package edu.sdccd.cisc191.b.client;

import javax.swing.JFrame;
import java.awt.*;

/**
 * Create the window used to start the game.
 *
 * @author Kim Lim
 * @see GameView
 */
public class Starter extends JFrame {

    /**
     * Set the dimensions, title, location, and visibility of the pane of which the game is played on.
     */
    public Starter()
    {
        Dimension size = Toolkit. getDefaultToolkit(). getScreenSize();
        add(new GameView());
        setTitle("Battle X Armada");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize((int) (size.getWidth() / 2),(int) (size.getHeight() - 35));
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
        new Starter();
    }
}