package edu.sdccd.cisc191.b.client;


import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import java.util.*;

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

    ArrayList<Point> bulletList;
    Point bullet;
    int bulletCount = 0;


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

        //Create our player's bullet
        bulletList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            bullet = new Point(0, 0);
            bulletList.add(bullet);
        }


        //Create our enemy ship type 1
        loadImgAlientType1();
        randomNum = new Random();
        enemyXPos = new int[GameView_WIDTH]; //has number of elements equal to GameView's width
        enemyYPos = new int[1000];
        alienType1 = new EnemyShip[100];
        int[] random = new int[1000]; //holds the positive number elements

        //set x and y coordinate for each type 1 enemy ship
        for (int x = 0; x < enemyXPos.length; x++) {
            enemyXPos[x] = randomNum.nextInt(GameView_WIDTH - imgAlienType1.getWidth());
        }
        for (int y = 0; y < enemyYPos.length; y++){
            random[y] = randomNum.nextInt(1000);

            //this will set the y positions over the top of the screen
            enemyYPos[y] = random[y] * -1;
        }

        for(int i = 0; i < alienType1.length; i++){
            alienType1[i] = new EnemyShip(enemyXPos[i], enemyYPos[i], 1);
        }


        if (animator == null || !ingame) {
            animator = new Thread(this);
            animator.start();
        }
        setDoubleBuffered(true);
    }

    public void paint(Graphics g)
    {
        super.paint(g);

        //represents our player
        g.drawImage(imgPlayer, player.getX(), player.getY(), this);
        if (player.moveLeft == true && player.x > 0){
            player.x -= player.getMoveSpeed();
        }
        if (player.moveRight == true && player.x < GameView_WIDTH - imgPlayer.getWidth() - 15){
            player.x += player.getMoveSpeed();
        }
        if (player.moveUp == true && player.y > 0){
            player.y -= player.getMoveSpeed();
        }
        if (player.moveDown == true && player.y < GameView_HEIGHT - imgPlayer.getHeight() -30){
            player.y += player.getMoveSpeed();
        }

        //draw player's bullet
        shoot();
        g.setColor(Color.RED);
        for (int i = 0; i < bulletList.size(); i++) {
            Point bullet = bulletList.get(i);
            g.fillRect(bullet.x, bullet.y, 2, 7);
        }


        //represents our enemy ship type 1
        moveDown();
        for (EnemyShip alien : alienType1) {
                g.drawImage(imgAlienType1, alien.getX(), alien.getY(), this);
        }


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
            if(key == 37) {//left arrow
                player.moveLeft = false;
            }
            if(key == 39) {//right arrow
                player.moveRight = false;
            }

            if(key == 38) {//up arrow) {
                player.moveUp = false;
            }
            if (key == 40) {//down arrow
                player.moveDown = false;
            }
        }

        public void keyPressed(KeyEvent e) {
            //System.out.println( e.getKeyCode());
            // message = "Key Pressed: " + e.getKeyCode();
            int key = e.getKeyCode();
            if(key == 37) {//left arrow
                player.moveLeft = true;
            }
            if(key == 39) {//right arrow
                player.moveRight = true;
            }

            if(key == 38) {//up arrow) {
                player.moveUp = true;
            }
            if (key == 40) {//down arrow
                player.moveDown = true;
            }

            if(key==32) { //space bar
                if (bulletCount > 99)
                    bulletCount = 0;
                bulletList.set(bulletCount, new Point(player.getX() + 25, player.getY()));
                bulletCount++;
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

    public void shoot(){
        for (int i = 0; i < bulletList.size(); i++) {
            Point bulletPoint = bulletList.get(i);
            bulletPoint.setLocation(bulletPoint.getX(), bulletPoint.getY() - 10);
            if (bulletPoint.getY() < 0)
                bulletList.set(i, new Point(0, 0));
        }
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

