package edu.sdccd.cisc191.b.client;

import javax.swing.JFrame;
import java.awt.*;
import java.net.*;
import java.io.*;

public class Starter extends JFrame {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

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

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) {
        new Starter();
    }
}