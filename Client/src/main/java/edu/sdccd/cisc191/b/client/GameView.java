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

    Point playerDefault;
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

        //creates our player object and sets its position to the bottom middle of the screen
        loadImgPlayer();
        playerDefault = new Point(GameView_WIDTH /2 - imgPlayer.getWidth()/2,
                                   GameView_HEIGHT - 100);
        player = new PlayerShip((int)playerDefault.getX(), (int)playerDefault.getY());
        player.setX((int)playerDefault.getX());
        player.setY((int)playerDefault.getY());
        playerScore = 0;

        //create our player's bullet
        bulletList = new LinkedList<>();

        for (int i = 0; i < 80; i++) {
            bulletHead = new Bullet(0,0);
            bulletList.add(bulletHead);
        }

        randomNum = new Random();

        //creates background stars
        stars = new Point[100];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Point(randomNum.nextInt(GameView_WIDTH - 60), randomNum.nextInt(1000));
        }

        //setup for creating enemy ships
        loadImgAlienType1();
        loadImgAlienType2();
        loadImgAlienType3();
        enemyXPos = new int[20];
        enemyYPos = new int[20];
        aliens = new EnemyShip[20];

        //generates enemy ships at random positions and types above the game screen
        for(int i = 0; i < aliens.length; i++){
            createShip(i);
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

        //moves bullets and enemies
        move();

        //represents our enemy ships
        for (EnemyShip alien: aliens) {

            //draws enemy ship depending on its type
            int type = alien.getType();
            if (type == 1) {
                g.drawImage(imgAlienType1, alien.getX(), alien.getY(), this);
            }
            if (type == 2) {
                g.drawImage(imgAlienType2, alien.getX(), alien.getY(), this);
            }
            if (type == 3) {
                g.drawImage(imgAlienType3, alien.getX(), alien.getY(), this);
            }

        }

        for (Point p: stars){
            g.setColor(new Color(107, 107, 107));
            g.fillOval((int)p.getX(), (int)p.getY(), 3, (int)(Math.random()*5 + 1));
        }

        //dictates in-game behavior
        if (ingame) {

            // g.drawImage(img,0,0,200,200 ,null);
            //represents our player
            if(player.isAlive()){

                //checks for movement and draws the player ship
                //g.drawImage(imgPlayer, player.getX(), player.getY(), this);
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
                g.drawImage(imgPlayer, player.getX(), player.getY(), this);

                //draw player's bullet
                g.setColor(Color.RED);
                for (Bullet b: bulletList) {
                    g.fillRect(b.getX(), b.getY(), 2, 7);
                }
            }

            //if the player loses all their lives, the game ends
            else{ ingame = false; }

        }

        //dictates post-game behavior
        if (ingame == false){

            //displays the "Game Over" text if player runs out of lives
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

    public void move(){

        //moves all bullets forward
        for(int i = 0; i < bulletList.size(); ++i) {
            bulletList.set(i, new Bullet(bulletList.get(i).getX(), bulletList.get(i).getY() - 10));
        }

        //moves all aliens down according to their moveSpeed
        for (int i = 0; i < aliens.length; i++){
            aliens[i].y += aliens[i].moveSpeed;
        }

        //moves all stars up one pixel
        for (int i = 0; i < stars.length; i++){
            stars[i].setLocation(stars[i].getX(), stars[i].getY() - 1);
        }
    }// end of move

    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == 37) { //left arrow
                player.moveLeft = false;
            }
            if(key == 39) { //right arrow
                player.moveRight = false;
            }

            if(key == 38) { //up arrow) {
                player.moveUp = false;
            }
            if (key == 40) { //down arrow
                player.moveDown = false;
            }
            if(key == 32) { // space bar
                player.bullet = false;
            }
        }

        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if(player.x > 0 && key == 37) { //left arrow
                player.moveLeft = true;
            }
            if(player.x < GameView_WIDTH - imgPlayer.getWidth() && key == 39) { //right arrow
                player.moveRight = true;
            }

            if(player.y > 0 && key == 38) { //up arrow
                player.moveUp = true;
            }
            if (player.y < GameView_HEIGHT - imgPlayer.getHeight() && key == 40) { //down arrow
                player.moveDown = true;
            }
            if(key == 32){
                if(player.bullet == false) {
                    player.bullet = true;
                    shoot();
                }
            }
        }//end of keyPress event
    }//end of class TAdapter

    //"loadImg" methods load associated images for each ship
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
        //add a new bullet to the bullet list
        Bullet newBullet = new Bullet(player.getX() + 25, player.getY());
        bulletList.add(newBullet);
        if(bulletList.size() == 1) {
            bulletHead = bulletList.get(0);
        }


        for (int i = 0; i < bulletList.size(); i++) {
            Bullet bullet = bulletList.get(i);

            //if a bullet is above the game screen, store a new bullet off screen
            if (bullet.getY() < 0) {
                bulletList.remove(bulletList.get(i));

            }


        }
    }//end of shoot

    public void collision(){

        //if a bullet hits an enemy ship, set that enemy ship's hit status to true, and store a new bullet off screen
        for (int i = 0; i < bulletList.size(); i++) {
            for (int j = 0; j < aliens.length; j++) {
                if (i != bulletList.size()) {
                    if (aliens[j].getHitBox().intersects(bulletList.get(i).getHitBox())) {
                        aliens[j].setHit(true);
                        bulletList.remove(bulletList.get(i));
                    }
                }
                else if(i>=1){
                    i--;
                }
            }
        }

        //check if any enemy ships have been bit by bullets
        for (int j = 0; j < aliens.length; j++) {
            //"eliminates" a hit enemy ship by creating a new one above the game screen and incrementing score
            if (aliens[j].isHit()) {
                playerScore += aliens[j].getScoreToDrop();
                createShip(j);
            }

            //creates a new enemy ship above the game screen if the enemy ship travels below the game screen
            else if(aliens[j].getY() >= GameView_HEIGHT){
                createShip(j);
            }
        }


        //if an enemy ship collides with the player,
        //player loses a life and is set to the default position
        //and a new enemy ship is created above the game screen
        for (int i = 0; i < aliens.length; i++) {
            if(aliens[i].getHitBox().intersects(player.getHitBox())){
                createShip(i);
                player.setX(playerDefault.x);
                player.setY(playerDefault.y);
                player.decrementLives();
                if (player.getLives() == 0){
                    player.setX(-50);
                    player.setAlive(false);
                }
            }
        }

        //if a star travels above the game screen, sets it at a random position below the game screen
        for (int i = 0; i < stars.length; i++){
            if (stars[i].getY() < 0){
                stars[i].setLocation(randomNum.nextInt(GameView_WIDTH - 60), GameView_HEIGHT);
            }
        }
    }//end of collision

    //generates a new enemy ship at a given index with a random position and type
    public void createShip(int index){

        //sets random x and y positions
        enemyXPos[index] = randomNum.nextInt(GameView_WIDTH - 60);
        enemyYPos[index] = randomNum.nextInt(1000) * -1;

        //probability system for creating certain types of ships
        int shipType = (int)(Math.random()*100 + 1);

        //75% probability of creating a Type 1 enemy ship
        if (shipType >= 1 && shipType <= 75)
            aliens[index] = new EnemyShip(enemyXPos[index], enemyYPos[index], 1);

        //20% probability of creating a Type 2 enemy ship
        else if (shipType > 75 && shipType <= 95)
            aliens[index] = new EnemyShip(enemyXPos[index], enemyYPos[index], 2);

        //5% probability of creating a Type 3 enemy ship
        else if (shipType > 95)
            aliens[index] = new EnemyShip(enemyXPos[index], enemyYPos[index], 3);
    }//end of createShip

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {}

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

