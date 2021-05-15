package edu.sdccd.cisc191.b.client;


import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;


public class GameView  extends JPanel implements Runnable, MouseListener
{
    Dimension size = Toolkit. getDefaultToolkit(). getScreenSize();
    boolean ingame = true;
    private Dimension d;
    int GameView_WIDTH = (int) (size.getWidth() / 2);
    int GameView_HEIGHT = (int) (size.getHeight() - 35);
    private Thread animator;

    Point playerLocation;
    PlayerShip player;
    BufferedImage imgPlayer;

    Random randomNum;
    int[] enemyXPos;
    int[] enemyYPos;
    EnemyShip[] alienType1;
    BufferedImage imgAlienType1;


    public GameView()
    {
        addKeyListener(new TAdapter());
        addMouseListener(this);
        setFocusable(true);
        d = new Dimension(GameView_WIDTH, GameView_HEIGHT);
        setBackground(Color.black);

        //Create our player object and set its x and y coordinates
        loadImgPlayer();
        playerLocation = new Point(GameView_WIDTH /2 - imgPlayer.getWidth()/2,
                                   GameView_HEIGHT - 100);
        player = new PlayerShip(playerLocation.x, playerLocation.y, 250, 10);
        player.setX(playerLocation.x);
        player.setY(playerLocation.y);




        //Create our enemy ship type 1
        loadImgAlientType1();
        randomNum = new Random();
        enemyXPos = new int[GameView_WIDTH]; //has number of elements equal to GameView's width
        enemyYPos = new int[1000];
        alienType1 = new EnemyShip[100];
        int[] random = new int[1000]; //holds the positive number elements



        for (int x = 0; x < enemyXPos.length; x++) {
            enemyXPos[x] = randomNum.nextInt(GameView_WIDTH - imgAlienType1.getWidth());
//            System.out.println(enemyXPos[x]);
        }
        for (int y = 0; y < enemyYPos.length; y++){
            random[y] = randomNum.nextInt(1000);

//            this will set the y positions over the top of the screen
            enemyYPos[y] = random[y] * -1;

//            enemyYPos[y] = randomNum.nextInt(640);
//            System.out.println(enemyYPos[y]);
        }

        for(int i = 0; i < alienType1.length; i++){
            alienType1[i] = new EnemyShip(enemyXPos[i], enemyYPos[i], 1);
        }


           /*
             try {
                img = ImageIO.read(this.getClass().getResource("mount.jpg"));
            } catch (IOException e) {
                 System.out.println("Image could not be read");
            // System.exit(1);
            }
            */
        if (animator == null || !ingame) {
            animator = new Thread(this);
            animator.start();
        }
        setDoubleBuffered(true);
    }

    public void paint(Graphics g)
    {
        super.paint(g);

        //issue: doesn't draw ship

        //represents our player
        g.drawImage(imgPlayer, player.getX(), player.getY(), this);
        if (player.moveLeft == true){
            player.x -= player.getMoveSpeed();
        }
        if (player.moveRight == true){
            player.x += player.getMoveSpeed();
        }
        if (player.moveUp == true){
            player.y -= player.getMoveSpeed();
        }
        if (player.moveDown == true){
            player.y += player.getMoveSpeed();
        }



        //represents our enemy ship type 1
        g.setColor(Color.red);
        moveDown();
        for (EnemyShip alien : alienType1) {
                g.drawImage(imgAlienType1, alien.getX(), alien.getY(), this);
        }


//g.fillOval(x,y,r,r);

//        Font small = new Font("Helvetica", Font.BOLD, 14);
//        FontMetrics metr = this.getFontMetrics(small);
//        g.setColor(Color.black);
//        g.setFont(small);
//        g.drawString(message, 10, d.height-60);

        if (ingame) {
            // g.drawImage(img,0,0,200,200 ,null);
        }
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void moveDown(){
        for (int i = 0; i < alienType1.length; i++){
            alienType1[i].y += alienType1[i].moveSpeed;
        }

    }
    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            player.moveLeft = false;
            player.moveUp = false;
            player.moveRight = false;
            player.moveDown = false;

        }

        public void keyPressed(KeyEvent e) {
//System.out.println( e.getKeyCode());
            // message = "Key Pressed: " + e.getKeyCode();
            int key = e.getKeyCode();
            if(key==37){//left arrow
                player.moveLeft = true;
            }
            if(key==38){//up arrow
                player.moveUp = true;
            }
            if(key==39){//right arrow
                player.moveRight = true;
            }
            if(key==40){//down arrow
                player.moveDown = true;
            }



        }

    }
    public void loadImgPlayer(){
        try{
            imgPlayer = ImageIO.read(this.getClass().getResourceAsStream("/playerShip.png"));
        }catch(Exception e){}


    }
    public void loadImgAlientType1(){
        try{
            imgAlienType1 = ImageIO.read(this.getClass().getResourceAsStream("/enemyType1.png"));
        }catch(Exception e){}
    }




    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }


    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();
        int animationDelay = 17;
        long time =
                System.currentTimeMillis();
        while (true) {//infinite loop
            // spriteManager.update();
            repaint();
            try {
                time += animationDelay;
                Thread.sleep(Math.max(0,time -
                        System.currentTimeMillis()));
            }catch (InterruptedException e) {
                System.out.println(e);
            }//end catch
        }//end while loop




    }//end of run

}//end of class

