package edu.sdccd.cisc191.b.client;


import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.JPanel;
import javax.imageio.*;
import java.awt.image.*;


public class GameView  extends JPanel implements Runnable, MouseListener
{
    Dimension size = Toolkit. getDefaultToolkit(). getScreenSize();
    boolean ingame = true;
    private Dimension d;
    int GameView_WIDTH = (int) (size.getWidth() / 2);
    int GameView_HEIGHT = (int) (size.getHeight() - 35);
    private Thread animator;

    BufferedImage imgLogo;
    Point[] stars;

    Point playerLocation;
    PlayerShip player;
    BufferedImage imgPlayer;
    int playerScore;
    User playerProfile;


    private LinkedList<Bullet> bulletList;
    Bullet bulletHead;
    int bulletCount = 0;


    Random randomNum;
    int[] enemyXPos;
    int[] enemyYPos;
    EnemyShip[] aliens;
    BufferedImage imgAlienType1;
    BufferedImage imgAlienType2;
    BufferedImage imgAlienType3;


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
        playerScore = 0;

        //Create our player's bullet
        bulletList = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            bulletHead = new Bullet(0,0);
            bulletList.add(bulletHead);
        }

        randomNum = new Random();

        //Create background stars
        stars = new Point[100];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Point(randomNum.nextInt(GameView_WIDTH - 60), randomNum.nextInt(1000));
        }

        //Create our enemy ship type 1
        loadImgAlienType1();
        loadImgAlienType2();
        loadImgAlienType3();
        enemyXPos = new int[GameView_WIDTH]; //has number of elements equal to GameView's width
        enemyYPos = new int[1000];
        aliens = new EnemyShip[20];
        int[] random = new int[1000]; //holds the positive number elements`

        //set x and y coordinate for each enemy ship
        for (int x = 0; x < enemyXPos.length; x++) {
            enemyXPos[x] = randomNum.nextInt(GameView_WIDTH - 60);
        }
        for (int y = 0; y < enemyYPos.length; y++){
            random[y] = randomNum.nextInt(1000);

            //this will set the y positions over the top of the screen
            enemyYPos[y] = randomNum.nextInt(1000) * -1;
        }


        for(int i = 0; i < aliens.length; i++){
            int shipType = (int)(Math.random()*100 + 1);
            if (shipType >= 1 && shipType <= 75)
                aliens[i] = new EnemyShip(enemyXPos[i], enemyYPos[i], 1);
            else if (shipType > 75 && shipType <= 90)
                aliens[i] = new EnemyShip(enemyXPos[i], enemyYPos[i], 2);
            else if (shipType > 90)
                aliens[i] = new EnemyShip(enemyXPos[i], enemyYPos[i], 3);
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


        /*
        Checks to see if any enemy collided with the bullet or the player
        if so, remove enemy if hit by bullet or remove enemy and player if they collide
         */
        collision();

        //represents our enemy ship type 1
        moveDown();
        for (int i = 0; i < aliens.length; i++) {
            int type = aliens[i].getType();
            if (type == 1) {
                g.drawImage(imgAlienType1, aliens[i].getX(), aliens[i].getY(), this);
            }
            if (type == 2) {
                g.drawImage(imgAlienType2, aliens[i].getX(), aliens[i].getY(), this);
            }
            if (type == 3) {
                g.drawImage(imgAlienType3, aliens[i].getX(), aliens[i].getY(), this);
            }

        }

        for (Point p: stars){
            g.setColor(new Color(107, 107, 107));
            g.fillOval((int)p.getX(), (int)p.getY(), 3, (int)(Math.random()*5 + 1));
        }

        if (ingame) {
            // g.drawImage(img,0,0,200,200 ,null);
            //represents our player
            if(player.isAlive()){
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
                if (bulletCount > 99)
                    bulletCount = 0;
                if (bulletCount % 10 == 0)
                    bulletList.set(bulletCount, new Bullet(player.getX() + 25, player.getY()));
                bulletCount++;
                shoot();
                g.setColor(Color.RED);
                for (int i = 0; i < bulletList.size(); i++) {
                    g.fillRect(bulletList.get(i).getX(), bulletList.get(i).getY(), 2, 7);
                }
            }else{ ingame = false; }

        }
        if (ingame == false){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Gameplay", Font.PLAIN, 100));
            g.drawString("Game Over", GameView_WIDTH / 2 - g.getFontMetrics().stringWidth("Game Over") /2,
                        GameView_HEIGHT / 2 - 25);
        }

        if(player.getLives() > -1){
            //displays player's lives
            g.setColor(Color.WHITE);
            g.setFont(new Font("Gameplay", Font.PLAIN, 15));
            g.drawString("Lives: " + player.getLives(), 5,15);

            //displays player's score
            g.setColor(Color.WHITE);
            g.drawString("Score: " + playerScore, GameView_WIDTH / 2,15);
        }


        Toolkit.getDefaultToolkit().sync();
        g.dispose();

    }// end of paint

    public void moveDown(){
        for (int i = 0; i < aliens.length; i++){
            aliens[i].y += aliens[i].moveSpeed;
        }
        for (int i = 0; i < stars.length; i++){
            stars[i].setLocation(stars[i].getX(), stars[i].getY() - 1);
        }
    }// end of moveDown

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
            if(player.x > 0 && key == 37) {//left arrow
                player.moveLeft = true;
            }
            if(player.x < GameView_WIDTH - imgPlayer.getWidth()
            && key == 39) {//right arrow
                player.moveRight = true;
            }

            if(player.y > 0 && key == 38) {//up arrow) {
                player.moveUp = true;
            }
            if (player.y < GameView_HEIGHT - imgPlayer.getHeight()
            && key == 40) {//down arrow
                player.moveDown = true;
            }

        }//end of keyPress event

    }//end of class TAdapter

    public void loadImgPlayer(){
        try{
            imgPlayer = ImageIO.read(this.getClass().getResourceAsStream("/playerShip.png"));
        }catch(Exception e){}


    }
    public void loadImgAlienType1(){
        try{
            imgAlienType1 = ImageIO.read(this.getClass().getResourceAsStream("/enemyType1.png"));
        }catch(Exception e){}
    }
    public void loadImgAlienType2(){
        try{
            imgAlienType2 = ImageIO.read(this.getClass().getResourceAsStream("/enemyType2.png"));
        }catch(Exception e){}
    }
    public void loadImgAlienType3(){
        try{
            imgAlienType3 = ImageIO.read(this.getClass().getResourceAsStream("/enemyType3.png"));
        }catch(Exception e){}
    }

    public void shoot() {
        for (int i = 0; i < bulletList.size(); i++) {
            Bullet bulletPoint = bulletList.get(i);
            bulletList.set(i, new Bullet(bulletPoint.getX(), bulletPoint.getY() - 10));

            if (bulletPoint.getY() < 0) {
                bulletList.set(i, new Bullet(0, 0));
            }

            for (int j = 0; j < aliens.length; j++) {
                if (aliens[j].getHitBox().intersects(bulletList.get(i).getHitBox())) {
                    aliens[j].setHit(true);
                    bulletList.remove(bulletList.get(i));
                    Bullet bullet = new Bullet(0, 0);
                    bulletList.add(bullet);
                }
            }
        }
    }//end of shoot

    public void collision(){
        for (int i = 0; i < bulletList.size(); i++) {
            for (int j = 0; j < aliens.length; j++) {
                int shipType = (int)(Math.random()*100 + 1);
                if (aliens[j].isHit()) {
                    enemyXPos[j] = randomNum.nextInt(GameView_WIDTH - 60);
                    enemyYPos[j] = randomNum.nextInt(1000) * -1;
                    EnemyShip enemy = aliens[j];
                    playerScore += enemy.getScoreToDrop();
                    if (shipType >= 1 && shipType <= 75)
                        aliens[j] = new EnemyShip(enemyXPos[j], enemyYPos[j], 1);
                    else if (shipType > 75 && shipType <= 90)
                        aliens[j] = new EnemyShip(enemyXPos[j], enemyYPos[j], 2);
                    else if (shipType > 90)
                        aliens[j] = new EnemyShip(enemyXPos[j], enemyYPos[j], 3);
                    aliens[j].setHit(false);
                }
                else if(aliens[j].getY() >= GameView_HEIGHT){
                    enemyXPos[j] = randomNum.nextInt(GameView_WIDTH - 60);
                    enemyYPos[j] = randomNum.nextInt(1000) * -1;
                    if (shipType >= 1 && shipType <= 75)
                        aliens[j] = new EnemyShip(enemyXPos[j], enemyYPos[j], 1);
                    else if (shipType > 75 && shipType <= 90)
                        aliens[j] = new EnemyShip(enemyXPos[j], enemyYPos[j], 2);
                    else if (shipType > 90)
                        aliens[j] = new EnemyShip(enemyXPos[j], enemyYPos[j], 3);
                    aliens[j].setHit(false);
                }
            }
        }

        for (int i = 0; i < aliens.length; i++) {
            if(aliens[i].getHitBox().intersects(player.getHitBox())){
                aliens[i].setY(-50);
                player.setX(playerLocation.x);
                player.setY(playerLocation.y);
                player.setLives(player.getLives() - 1);
                if (player.getLives() == 0){
                    player.setX(-50);
                    player.setAlive(false);
                }
            }
        }

        for (int i = 0; i < stars.length; i++){
            if (stars[i].getY() < 0){
                stars[i].setLocation(randomNum.nextInt(GameView_WIDTH - 60), GameView_HEIGHT);
            }
        }

    }//end of collision

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

