package edu.sdccd.cisc191.b.client;

import javax.swing.JFrame;
import java.awt.*;

public class Starter extends JFrame {
    GameView g = new GameView();

    public Starter()
    {
        Dimension size = Toolkit. getDefaultToolkit(). getScreenSize();
        add(g);
        setTitle("Battle X Armada");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize((int) (size.getWidth() / 2),(int) (size.getHeight() - 35));
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

    }

    public static void main(String[] args) {
        new Starter();
    }
}