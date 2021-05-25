package edu.sdccd.cisc191.b.client;

import edu.sdccd.cisc191.b.UserProfileRequest;
import edu.sdccd.cisc191.b.UserProfileResponse;
import edu.sdccd.cisc191.b.UserScoreRequest;
import edu.sdccd.cisc191.b.UserScoreResponse;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.io.*;
import java.net.Socket;
import java.util.*;
import javax.swing.JPanel;
import javax.imageio.*;
import java.awt.image.*;

/**
 * The GameView program provides the main player controls and visuals for the "Battle X Armada"
 * arcade-shooter-style game.
 *
 *
 */

public class GameView  extends JPanel implements Runnable, MouseListener
{
    Dimension size = Toolkit. getDefaultToolkit(). getScreenSize();
    boolean ingame = false;
    private Dimension d;
    int GameView_WIDTH = (int) (size.getWidth() / 2);
    int GameView_HEIGHT = (int) (size.getHeight() - 35);
    private Thread animator;

    BufferedImage imgLogo;
    Point[] stars;
    boolean login = true;
    boolean loginWarning = false;
    boolean started = false;
    int cursorCount = 0;

    Point playerDefault;
    PlayerShip player;
    BufferedImage imgPlayer;
    int playerScore;
    User playerProfile;
    String playerName = "";
    ArrayList<UserScoreResponse> leaderBoard;

    private LinkedList<Bullet> bulletList;
    Bullet bulletHead;

    Random randomNum;
    int[] enemyXPos;
    int[] enemyYPos;
    EnemyShip[] aliens;
    BufferedImage imgAlienType1;
    BufferedImage imgAlienType2;
    BufferedImage imgAlienType3;

    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public GameView()
    {
        addKeyListener(new TAdapter());
        addMouseListener(this);
        setFocusable(true);
        d = new Dimension(GameView_WIDTH, GameView_HEIGHT);
        setBackground(Color.black);

        //setup for the login screen
        loadImgLogo();

        //creates our player ship object and sets its position to the bottom middle of the screen (default position)
        loadImgPlayer();
        playerDefault = new Point(GameView_WIDTH /2 - imgPlayer.getWidth()/2,
                                   GameView_HEIGHT - 100);
        player = new PlayerShip((int)playerDefault.getX(), (int)playerDefault.getY());
        player.setX((int)playerDefault.getX());
        player.setY((int)playerDefault.getY());
        playerScore = 0;

        //creates our player's bullets
        bulletList = new LinkedList<>();

        for (int i = 0; i < 80; i++) {
            bulletHead = new Bullet(0,0);
            bulletList.add(bulletHead);
        }

        randomNum = new Random();

        //creates background stars
        stars = new Point[150];
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

        leaderBoard = new ArrayList<>();

        if (animator == null || !ingame) {
            animator = new Thread(this);
            animator.start();
        }
        setDoubleBuffered(true);
    }

    /**
     * This is the method that updates the state of every visual in the game, from Ships to the score,
     * to even the stars in the background. paint() is repeatedly called in GameView's run() method.
     *
     * While playing:
     *      paint() checks for the states of objects using collision(), and moves objects using move().
     *      The enemy ships and header details ("Lives:" and "Score:") are drawn
     *        regardless of the status of the ingame boolean.
     *      If ingame is true, paint() checks for movement then draws the player, along with any active
     *        bullets, if the player is alive. If the player is no longer alive, ingame is set to false.
     *      If ingame is false, "Game Over" is painted along with the leaderboard. The player is given
     *        the option to play again or quit.
     */
    public void paint(Graphics g)
    {
        super.paint(g);

        //checks the status of background stars and moves them up
        //stars are present no matter what "screen" GameView is on
        updateStars();

        //  draws background stars
        for (Point p: stars){
            g.setColor(new Color(107, 107, 107));
            g.fillOval((int)p.getX(), (int)p.getY(), 3, (int)(Math.random()*5 + 1));
        }

        if (login) {

            //draws the "Battle X Armada" logo; blame Joaquin for how bad it looks
            //graphic design is my passion
            g.drawImage(imgLogo, GameView_WIDTH/2 - imgLogo.getWidth()/2, 50, this);
            g.setColor(Color.white);

            //draw name prompt text
            g.setFont(new Font("Gameplay", Font.PLAIN, 20));
            g.drawString("Enter your name to begin", GameView_WIDTH/2 - g.getFontMetrics().stringWidth("Enter your name to begin")/2,
                    (GameView_HEIGHT/4)*3);
            g.drawString(playerName, GameView_WIDTH/2 - g.getFontMetrics().stringWidth(playerName)/2,
                    (GameView_HEIGHT/4)*3 + 30);
            g.setFont(new Font("Gameplay", Font.PLAIN, 15));
            g.drawString("Name can only be numbers and letters", GameView_WIDTH/2 - g.getFontMetrics().stringWidth("Name can only be numbers and letters")/2,
                    (GameView_HEIGHT/4)*3 + 55);

            //draw warning text if a name is too long or too short
            if (loginWarning) {
                g.setColor(Color.red);
                g.drawString("Name must be at least 3 characters long", GameView_WIDTH/2 - g.getFontMetrics().stringWidth("Name must be at least 3 characters long")/2,
                        (GameView_HEIGHT/4)*3 + 75);
                g.setColor(Color.white);
            }

            g.setFont(new Font("Gameplay", Font.PLAIN, 12));
            g.drawString("Created by: Joaquin Dicang, Sholehani Hafezi, Shubham Joshi, Kim Lim, and Maria Lourdes Thomas",
                    GameView_WIDTH/2 - g.getFontMetrics().stringWidth("Created by: Joaquin Dicang, Sholehani Hafezi, Shubham Joshi, Kim Lim, and Maria Lourdes Thomas")/2,
                    GameView_HEIGHT - 45);
        }

        if (started) {

            //represents header details
            //  header details display even after the player loses, to show the final values of lives and score
            if (player.getLives() > -1) {
                g.setColor(Color.WHITE);

                g.setFont(new Font("Arial", Font.PLAIN, 15));

                //draws lives and score data on the top left of the game screen
                g.drawString("Lives: " + player.getLives(), 5, 20);
                g.drawString("Score: " + playerScore, 5, 45);
            }

            //dictates in-game behavior
            if (ingame) {

                //draws controls on the top right of the game screen
                g.drawString("Press ARROW KEYS to move", GameView_WIDTH - g.getFontMetrics().stringWidth("Press ARROW KEYS to move") - 20, 20);
                g.drawString("Press SPACE BAR to shoot", GameView_WIDTH - g.getFontMetrics().stringWidth("Press SPACE BAR to shoot") - 20, 45);

                //checks the states of any objects in the game and alters objects or lists appropriately
                collision();

                //moves bullets, enemies, and stars
                move();

                //represents our enemy ships
                for (EnemyShip alien : aliens) {

                    //draws enemy ships depending on their type
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

                //represents our player
                if (player.isAlive()) {

                    //checks for movement and draws the player ship
                    if (player.moveLeft && player.x > 0) {
                        player.x -= player.getMoveSpeed();
                    }
                    if (player.moveRight && player.x < GameView_WIDTH - imgPlayer.getWidth() - 15) {
                        player.x += player.getMoveSpeed();
                    }
                    if (player.moveUp && player.y > 0) {
                        player.y -= player.getMoveSpeed();
                    }
                    if (player.moveDown && player.y < GameView_HEIGHT - imgPlayer.getHeight() - 30) {
                        player.y += player.getMoveSpeed();
                    }
                    g.drawImage(imgPlayer, player.getX(), player.getY(), this);

                    //draws player's bullet
                    g.setColor(Color.RED);
                    for (Bullet b : bulletList) {
                        g.fillRect(b.getX(), b.getY(), 2, 7);
                    }
                }

                //if the player loses all 3 lives, the game ends
                else { ingame = false; }
            }

            //dictates post-game behavior
            if (!ingame) {

                //displays the "Game Over" text if player runs out of lives
                g.setColor(Color.WHITE);
                g.setFont(new Font("Gameplay", Font.PLAIN, 100));
                g.drawString("Game Over", GameView_WIDTH / 2 - g.getFontMetrics().stringWidth("Game Over") / 2,
                        GameView_HEIGHT/4);

                //draws title of the leaderboard
                g.setFont(new Font("Gameplay", Font.PLAIN, 30));
                g.drawString("Leaderboard", (GameView_WIDTH/2) - g.getFontMetrics().stringWidth("Leaderboard")/2,
                        GameView_HEIGHT/4 + 150);
                g.drawLine(GameView_WIDTH/5, GameView_HEIGHT/4 + 155, (GameView_WIDTH/5)*4, GameView_HEIGHT/4 + 155);

                //draws top 10 highscores and player names
                g.setColor(Color.WHITE);
                g.setFont(new Font("Gameplay", Font.PLAIN, 20));

                for (int i = 0; i < leaderBoard.size(); i++) {
                    UserScoreResponse r = leaderBoard.get(i);
                    g.drawString(r.getUserName(), GameView_WIDTH / 5, GameView_HEIGHT/4 + 190 + (25 * i));
                    g.drawString(String.format("%d", r.getHighScore()),
                            ((GameView_WIDTH / 5) * 4) - g.getFontMetrics().stringWidth(r.getHighScore() + ""),
                            GameView_HEIGHT/4 + 190 + (25 * i));
                }

                //Displays "Would you like to play again? prompt and instructions"
                g.setColor(Color.WHITE);
                g.setFont(new Font("Gameplay", Font.PLAIN, 30));
                g.drawString("Would you like to play again?", GameView_WIDTH / 2
                                - g.getFontMetrics().stringWidth("Would you like to play again?") / 2,
                        (GameView_HEIGHT/4)*3);

                g.setFont(new Font("Gameplay", Font.PLAIN, 15));
                g.drawString("Press Y to play again",
                        GameView_WIDTH / 2 - g.getFontMetrics().stringWidth("Press Y to play again") / 2,
                        (GameView_HEIGHT/4)*3 + 40);
                g.drawString("Press N to exit",
                        GameView_WIDTH / 2 - g.getFontMetrics().stringWidth("Press N to exit") / 2,
                        (GameView_HEIGHT/4)*3 + 60);



            }//end of if(ingame)
        }//end of if(started)

        Toolkit.getDefaultToolkit().sync();
        g.dispose();

    }// end of paint

    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();

            if (started && ingame) {
                if (key == 37) { //left arrow
                    player.moveLeft = false;
                }
                if (key == 39) { //right arrow
                    player.moveRight = false;
                }

                if (key == 38) { //up arrow) {
                    player.moveUp = false;
                }
                if (key == 40) { //down arrow
                    player.moveDown = false;
                }
                if (key == 32) { // space bar
                    player.bullet = false;
                }
            }
        }

        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            //inputs to get the player's name before the game starts
            if (login) {

                //only add letters to the name up to 15 characters
                if (key == 65){ if (playerName.length() < 15) playerName += "A";  }
                if (key == 66){ if (playerName.length() < 15) playerName += "B"; }
                if (key == 67){ if (playerName.length() < 15) playerName += "C"; }
                if (key == 68){ if (playerName.length() < 15) playerName += "D"; }
                if (key == 69){ if (playerName.length() < 15) playerName += "E"; }
                if (key == 70){ if (playerName.length() < 15) playerName += "F"; }
                if (key == 71){ if (playerName.length() < 15) playerName += "G"; }
                if (key == 72){ if (playerName.length() < 15) playerName += "H"; }
                if (key == 73){ if (playerName.length() < 15) playerName += "I"; }
                if (key == 74){ if (playerName.length() < 15) playerName += "J"; }
                if (key == 75){ if (playerName.length() < 15) playerName += "K"; }
                if (key == 76){ if (playerName.length() < 15) playerName += "L"; }
                if (key == 77){ if (playerName.length() < 15) playerName += "M"; }
                if (key == 78){ if (playerName.length() < 15) playerName += "N"; }
                if (key == 79){ if (playerName.length() < 15) playerName += "O"; }
                if (key == 80){ if (playerName.length() < 15) playerName += "P"; }
                if (key == 81){ if (playerName.length() < 15) playerName += "Q"; }
                if (key == 82){ if (playerName.length() < 15) playerName += "R"; }
                if (key == 83){ if (playerName.length() < 15) playerName += "S"; } //I'm so sorry -Joaquin
                if (key == 84){ if (playerName.length() < 15) playerName += "T"; }
                if (key == 85){ if (playerName.length() < 15) playerName += "U"; }
                if (key == 86){ if (playerName.length() < 15) playerName += "V"; }
                if (key == 87){ if (playerName.length() < 15) playerName += "W"; }
                if (key == 88){ if (playerName.length() < 15) playerName += "X"; }
                if (key == 89){ if (playerName.length() < 15) playerName += "Y"; }
                if (key == 90){ if (playerName.length() < 15) playerName += "Z"; }
                if (key == 48){ if (playerName.length() < 15) playerName += "0"; }
                if (key == 49){ if (playerName.length() < 15) playerName += "1"; }
                if (key == 50){ if (playerName.length() < 15) playerName += "2"; }
                if (key == 51){ if (playerName.length() < 15) playerName += "3"; }
                if (key == 52){ if (playerName.length() < 15) playerName += "4"; }
                if (key == 53){ if (playerName.length() < 15) playerName += "5"; }
                if (key == 54){ if (playerName.length() < 15) playerName += "6"; }
                if (key == 55){ if (playerName.length() < 15) playerName += "7"; }
                if (key == 56){ if (playerName.length() < 15) playerName += "8"; }
                if (key == 57){ if (playerName.length() < 15) playerName += "9"; }

                //removes letters from the name
                if (key == 8){
                    if (playerName.length() > 0) { playerName = playerName.substring(0, playerName.length() - 1); }
                }

                //enters the name and starts the game
                if (key == 10){

                    //checks if the entered name is the appropriate length, then "logs in"
                    if (playerName.length() > 2 && playerName.length() <= 15) {


                        //contact server and send name to log in
                        //TODO: make sure this doesn't blow up with networking
                        Thread sendName = new Thread( () -> { loginRequest(playerName); } );
                        sendName.start();
                        while (sendName.isAlive()) {
                            try {
                                sendName.join();
                            } catch(InterruptedException ex) {ex.printStackTrace();}
                        }

                        //turn off intro screen
                        login = false;
                        loginWarning = false;

                        //start playing
                        ingame = true;
                        started = true;
                    }

                    //if the entered name is not long enough, flag to print a warning on the screen
                    if (playerName.length() < 3) {
                        loginWarning = true;
                    }
                }

            }

            //inputs for player controls during the game
            if (started && ingame) {
                if (player.x > 0 && key == 37) { //left arrow
                    player.moveLeft = true;
                }
                if (player.x < GameView_WIDTH - imgPlayer.getWidth() && key == 39) { //right arrow
                    player.moveRight = true;
                }

                if (player.y > 0 && key == 38) { //up arrow
                    player.moveUp = true;
                }
                if (player.y < GameView_HEIGHT - imgPlayer.getHeight() && key == 40) { //down arrow
                    player.moveDown = true;
                }
                if (key == 32) {
                    if (!player.bullet) {
                        player.bullet = true;
                        shoot();
                    }
                }
            }

            if(started && !ingame) {
                if (key == 89) {
                    resetGame();
                    player.setAlive(true);
                    ingame = true;
                }

                if (key == 8) {
                    //TODO: end game
                }
            }
        }//end of keyPress event
    }//end of class TAdapter

    //"loadImg" methods load associated images for the game
    public void loadImgLogo() {
        try {
            imgLogo = ImageIO.read(this.getClass().getResourceAsStream("/BxA Logo.png"));
        } catch(Exception e) {}
    }

    public void loadImgPlayer() {
        try {
            imgPlayer = ImageIO.read(this.getClass().getResourceAsStream("/playerShip.png"));
        } catch(Exception e){}
    }

    public void loadImgAlienType1() {
        try {
            imgAlienType1 = ImageIO.read(this.getClass().getResourceAsStream("/enemyType1.png"));
        } catch(Exception e) {}
    }

    public void loadImgAlienType2() {
        try {
            imgAlienType2 = ImageIO.read(this.getClass().getResourceAsStream("/enemyType2.png"));
        } catch(Exception e) {}
    }

    public void loadImgAlienType3() {
        try {
            imgAlienType3 = ImageIO.read(this.getClass().getResourceAsStream("/enemyType3.png"));
        } catch(Exception e) {}
    }

    public void shoot() {

        //add a new bullet to the bullet list
        Bullet newBullet = new Bullet(player.getX() + 25, player.getY());
        bulletList.add(newBullet);
        if (bulletList.size() == 1) {
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

    public void move(){

        //moves all bullets forward
        for(int i = 0; i < bulletList.size(); ++i) {
            bulletList.set(i, new Bullet(bulletList.get(i).getX(), bulletList.get(i).getY() - 10));
        }

        //moves all aliens down according to their moveSpeed
        for (int i = 0; i < aliens.length; i++){
            aliens[i].y += aliens[i].moveSpeed;
        }
    }// end of move

    public void collision() {

        //if a bullet hits an enemy ship, set that enemy ship's hit status to true, and store a new bullet off screen
        for (int i = 0; i < bulletList.size(); i++) {
            for (int j = 0; j < aliens.length; j++) {
                if (i != bulletList.size()) {
                    if (aliens[j].getHitBox().intersects(bulletList.get(i).getHitBox())) {
                        aliens[j].setHit(true);
                        bulletList.remove(bulletList.get(i));
                    }
                }
                else if (i>=1) {
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
            else if (aliens[j].getY() >= GameView_HEIGHT) {
                createShip(j);
            }
        }

        //if an enemy ship collides with the player,
        //player loses a life and is set to the default position
        //and a new enemy ship is created above the game screen
        for (int i = 0; i < aliens.length; i++) {
            if (aliens[i].getHitBox().intersects(player.getHitBox())) {
                createShip(i);
                player.setX(playerDefault.x);
                player.setY(playerDefault.y);
                player.decrementLives();

                //if the player dies, player's alive status is set to false
                //  and the game sends a leaderBoardRequest()
                if (player.getLives() == 0) {
                    player.setX(-50);
                    player.setAlive(false);

                    Thread sendRequest = new Thread( () -> { leaderBoardRequest(playerName, playerScore); } );
                    sendRequest.start();
                    while (sendRequest.isAlive()) {
                        try {
                            sendRequest.join();
                        } catch(InterruptedException e) { e.printStackTrace(); }
                    }

                }
            }//end of outer if
        }//end of for
    }//end of collision

    //generates a new enemy ship at a given index with a random position and type
    public void createShip(int index) {

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

    public void updateStars() {

        //if a star travels below the game screen, sets it at a random position above the game screen
        for (int i = 0; i < stars.length; i++) {
            if (stars[i].getY() > GameView_HEIGHT + 1)
                stars[i].setLocation(randomNum.nextInt(GameView_WIDTH - 60), - 1);
        }

        //moves all stars down
        for (int i = 0; i < stars.length; i++)
            stars[i].setLocation(stars[i].getX(), stars[i].getY() + 1);

    }//end of updateStars

    //resets all game objects to their default values
    public void resetGame() {

        //resets bullets
        for(int i = 0; i < bulletList.size(); ++i) {
            bulletList.set(i, new Bullet(0, 0));
            bulletHead = bulletList.get(i);
        }

        //resets player
        player = new PlayerShip((int)playerDefault.getX(), (int)playerDefault.getY());
        player.setX((int)playerDefault.getX());
        player.setY((int)playerDefault.getY());
        playerScore = 0;

        //resets enemy ships
        for(int i = 0; i < aliens.length; i++){
            createShip(i);
        }

        //resets leaderboard
        leaderBoard = new ArrayList<>();
    }

    public void startConnection(String ip, int port) throws Exception {
        clientSocket = new Socket(ip, port);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }


    public void loginRequest(String userName) {
        try {

            //start a connection with the server, then send a UserProfileRequest
            startConnection("127.0.0.1", 4444);
            out.writeObject(new UserProfileRequest(userName));

            //receive a UserProfileResponse, then stop the connection
            UserProfileResponse response = (UserProfileResponse) in.readObject();
            stopConnection();

            //create a User object for the player using the received login information
            playerProfile = new User(response.getUserName(), response.getGamesPlayed(), response.getHighScore());

        } catch (Exception e) { e.printStackTrace(); }
    }

    public void leaderBoardRequest(String userName, int userScore) {
        try {

            //start a connection with the server, then send a UserScoreRequest
            startConnection("127.0.0.1", 4444);
            out.writeObject(new UserScoreRequest(playerName, playerScore));

            //receive an ArrayList<UserScoreResponse> of the 10 highest scores, then stop the connection
            ArrayList<UserScoreResponse> users = (ArrayList<UserScoreResponse>)in.readObject();
            stopConnection();

            //transfer received data to the leaderBoard ArrayList
            leaderBoard.addAll(users);

        } catch (Exception e) { e.printStackTrace(); }
    }

    //the mouse is not used, except to interact with buttons
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
            } catch (InterruptedException e) {
                System.out.println(e);
            }//end catch
        }//end while loop
    }//end of run
}//end of class

