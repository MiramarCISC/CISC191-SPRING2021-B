package edu.sdccd.cisc191.b.client;

import edu.sdccd.cisc191.b.*;

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
 * @author Joaquin Dicang, Sholehani Hafezi, Shubham Joshi, Kim Lim, Maria Lourdes Thomas
 *
 * The GameView program provides the main player controls and visuals for the "Battle X Armada"
 * arcade-shooter-style game.
 */

public class GameView  extends JPanel implements Runnable, MouseListener
{
    Dimension size = Toolkit. getDefaultToolkit(). getScreenSize();
    boolean ingame = false;
    private Dimension d;
    int GameView_WIDTH = (int) (size.getWidth() / 2);
    int GameView_HEIGHT = (int) (size.getHeight() - 35);
    private Thread animator;
    String fontName;

    BufferedImage imgLogo;
    Point[] stars;
    boolean login = true;
    boolean loginWarning = false;
    boolean started = false;

    Point playerDefault;
    PlayerShip player;
    BufferedImage imgPlayer;
    int playerScore;
    User playerProfile;
    String playerName = "";
    ArrayList<UserScoreResponse> leaderBoard;

    private LinkedList<Bullet> bulletList;

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

        //setup for UI
        fontName = "Arial";
        loadImages();

        //creates our player ship object and sets its position to the bottom middle of the screen (default position)
        playerDefault = new Point(GameView_WIDTH /2 - imgPlayer.getWidth()/2,
                                   GameView_HEIGHT - 100);
        player = new PlayerShip((int)playerDefault.getX(), (int)playerDefault.getY());
        player.setX((int)playerDefault.getX());
        player.setY((int)playerDefault.getY());
        playerScore = 0;

        //creates our player's bullets
        bulletList = new LinkedList<>();
        for (int i = 0; i < 80; i++)
            bulletList.add(new Bullet(0,0));

        randomNum = new Random();

        //creates background stars
        stars = new Point[150];
        for (int i = 0; i < stars.length; i++)
            stars[i] = new Point(randomNum.nextInt(GameView_WIDTH - 60), randomNum.nextInt(1000));

        //setup for creating enemy ships
        enemyXPos = new int[20];
        enemyYPos = new int[20];
        aliens = new EnemyShip[20];

        //generates enemy ships at random positions and types above the game screen
        for(int i = 0; i < aliens.length; i++){
            createShip(i);
        }

        //initializes leaderboard
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
     * Before playing:
     *      paint() draws the "Battle X Armada" game logo at the top of the screen.
     *      The user is prompted to enter their username, and paint() draws the characters that are typed.
     *      If the user enters a name that is too short, paint() draws a warning message
     *
     * While playing:
     *      paint() checks for the states of objects using collision(), and moves objects using move().
     *      The header details ("Lives:" and "Score:") are drawn at the top left of the screen, and the
     *          player's user profile details are drawn at the bottom of the screen, regardless of the
     *          status of <b>ingame</b>.
     *      If <b>ingame</b> is true, paint() checks for movement then draws the player, along with and
     *          active bullets, if the player is alive. If the player is no longer alive, <b>ingame</b>
     *          is set to false. The game's controls are drawn at the top right of the screen while playing
     *      If <b>ingame</b> is false, "Game Over" is painted along with the leaderboard. The player is given
     *          the option to play again or quit.
     */
    public void paint(Graphics g)
    {
        super.paint(g);

        //checks the status of background stars and moves them up
        //stars are present no matter what "screen" the game is on
        updateStars();

        //  draws background stars
        for (Point p: stars){
            g.setColor(new Color(107, 107, 107));
            g.fillOval((int)p.getX(), (int)p.getY(), 3, (int)(Math.random()*5 + 1));
        }

        //if the player is on the opening screen of the game
        if (login) {

            //draws the "Battle X Armada" logo; blame Joaquin for how bad it looks
            //  graphic design is my passion
            g.drawImage(imgLogo, GameView_WIDTH/2 - imgLogo.getWidth()/2, 50, this);
            g.setColor(Color.white);

            //draw name prompt text
            g.setFont(new Font(fontName, Font.PLAIN, 20));
            g.drawString("Enter your name to begin", GameView_WIDTH/2 - g.getFontMetrics().stringWidth("Enter your name to begin")/2,
                    (GameView_HEIGHT/4)*3);
            g.drawString(playerName, GameView_WIDTH/2 - g.getFontMetrics().stringWidth(playerName)/2,
                    (GameView_HEIGHT/4)*3 + 30);
            g.setFont(new Font(fontName, Font.PLAIN, 15));
            g.drawString("Name can only be numbers and letters", GameView_WIDTH/2 - g.getFontMetrics().stringWidth("Name can only be numbers and letters")/2,
                    (GameView_HEIGHT/4)*3 + 55);

            //draw warning text if a name is too long or too short
            if (loginWarning) {
                g.setColor(Color.red);
                g.drawString("Name must be at least 3 characters long", GameView_WIDTH/2 - g.getFontMetrics().stringWidth("Name must be at least 3 characters long")/2,
                        (GameView_HEIGHT/4)*3 + 75);
                g.setColor(Color.white);
            }

            g.drawString("Press ENTER to play", GameView_WIDTH/2 - g.getFontMetrics().stringWidth("Press ENTER to play")/2,
                    (GameView_HEIGHT/4)*3 + 95);
            g.drawString("Press SHIFT to exit", GameView_WIDTH/2 - g.getFontMetrics().stringWidth("Press SHIFT to exit")/2,
                    (GameView_HEIGHT/4)*3 + 115);

            g.setFont(new Font(fontName, Font.PLAIN, 12));
            g.drawString("Created by: Joaquin Dicang, Sholehani Hafezi, Shubham Joshi, Kim Lim, and Maria Lourdes Thomas",
                    GameView_WIDTH/2 - g.getFontMetrics().stringWidth("Created by: Joaquin Dicang, Sholehani Hafezi, Shubham Joshi, Kim Lim, and Maria Lourdes Thomas")/2,
                    GameView_HEIGHT - 10);
        }

        //if the player has started playing
        if (started) {

            //dictates in-game behavior
            if (ingame) {

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
                    if (player.isMoveLeft() && player.getX() > 0) {
                        player.setX(player.getX() - player.getMoveSpeed());
                    }
                    if (player.isMoveRight() && player.getX() < GameView_WIDTH - imgPlayer.getWidth() - 15) {
                        player.setX(player.getX() + player.getMoveSpeed());
                    }
                    if (player.isMoveUp() && player.getY() > 0) {
                        player.setY(player.getY() - player.getMoveSpeed());
                    }
                    if (player.isMoveDown() && player.getY() < GameView_HEIGHT - imgPlayer.getHeight() - 30) {
                        player.setY(player.getY() + player.getMoveSpeed());
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

                //draws controls on the top right of the game screen
                g.setFont(new Font(fontName, Font.PLAIN, 20));
                g.setColor(Color.white);
                g.drawString("Press ARROW KEYS to move", GameView_WIDTH - g.getFontMetrics().stringWidth("Press ARROW KEYS to move") - 5, 30);
                g.drawString("Press SPACE BAR to shoot", GameView_WIDTH - g.getFontMetrics().stringWidth("Press SPACE BAR to shoot") - 5, 60);

            }

            //dictates post-game behavior
            if (!ingame) {

                //displays the "Game Over" text if player runs out of lives
                g.setColor(Color.WHITE);
                g.setFont(new Font(fontName, Font.PLAIN, 100));
                g.drawString("Game Over", GameView_WIDTH / 2 - g.getFontMetrics().stringWidth("Game Over") / 2,
                        GameView_HEIGHT/4);

                //draws title of the leaderboard
                g.setFont(new Font(fontName, Font.PLAIN, 30));
                g.drawString("Leaderboard", (GameView_WIDTH/2) - g.getFontMetrics().stringWidth("Leaderboard")/2,
                        GameView_HEIGHT/4 + 150);
                g.drawLine(GameView_WIDTH/5, GameView_HEIGHT/4 + 155, (GameView_WIDTH/5)*4, GameView_HEIGHT/4 + 155);

                //draws top 10 highscores and player names
                g.setColor(Color.WHITE);
                g.setFont(new Font(fontName, Font.PLAIN, 20));

                for (int i = 0; i < leaderBoard.size(); i++) {
                    UserScoreResponse r = leaderBoard.get(i);
                    g.drawString(r.getUserName(), GameView_WIDTH / 5, GameView_HEIGHT/4 + 190 + (25 * i));
                    g.drawString(String.format("%d", r.getHighScore()),
                            ((GameView_WIDTH / 5) * 4) - g.getFontMetrics().stringWidth(r.getHighScore() + ""),
                            GameView_HEIGHT/4 + 190 + (25 * i));
                }

                //Displays "Would you like to play again? prompt and instructions"
                g.setColor(Color.WHITE);
                g.setFont(new Font(fontName, Font.PLAIN, 30));
                g.drawString("Would you like to play again?", GameView_WIDTH / 2
                                - g.getFontMetrics().stringWidth("Would you like to play again?") / 2,
                        (GameView_HEIGHT/4)*3);

                g.setFont(new Font(fontName, Font.PLAIN, 15));
                g.drawString("Press ENTER to play again",
                        GameView_WIDTH / 2 - g.getFontMetrics().stringWidth("Press ENTER to play again") / 2,
                        (GameView_HEIGHT/4)*3 + 40);
                g.drawString("Press SHIFT to exit",
                        GameView_WIDTH / 2 - g.getFontMetrics().stringWidth("Press SHIFT to exit") / 2,
                        (GameView_HEIGHT/4)*3 + 60);

            }//end of if(ingame)

            //draws lives and score data on the top left of the game screen
            g.setFont(new Font(fontName, Font.PLAIN, 20));
            g.setColor(Color.WHITE);
            g.drawString("Lives: " + player.getLives(), 5, 30);
            g.drawString("Score: " + playerScore, 5, 60);

            //draws the user's profile information
            g.setFont(new Font(fontName, Font.PLAIN, 12));
            g.drawString(playerProfile + "",
                    GameView_WIDTH/2 - g.getFontMetrics().stringWidth(playerProfile + "")/2,
                    GameView_HEIGHT - 10);

        }//end of if(started)

        Toolkit.getDefaultToolkit().sync();
        g.dispose();

    }// end of paint

    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();

            if (started && ingame) {
                if (key == 37) { //left arrow
                    player.setMoveLeft(false);
                }
                if (key == 39) { //right arrow
                    player.setMoveRight(false);
                }

                if (key == 38) { //up arrow) {
                    player.setMoveUp(false);
                }
                if (key == 40) { //down arrow
                    player.setMoveDown(false);
                }
                if (key == 32) { // space bar
                    player.setBullet(false);
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
                if (key == 32){ if (playerName.length() < 15) playerName += " "; }

                //removes letters from the name
                if (key == 8) { //backspace
                    if (playerName.length() > 0) { playerName = playerName.substring(0, playerName.length() - 1); }
                }

                //enters the name and starts the game
                if (key == 10) { //enter

                    //checks if the entered name is the appropriate length, then "logs in"
                    if (playerName.length() > 2 && playerName.length() <= 15) {

                        //contact server and send name to log in
                        Thread sendName = new Thread( () -> { profileRequest(playerName); } );
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
                    if (playerName.length() < 3) { loginWarning = true; }

                }
            }

            //inputs for player controls during the game
            if (started && ingame) {
                if (player.getX() > 0 && key == 37) { //left arrow
                    player.setMoveLeft(true);
                }
                if (player.getX() < GameView_WIDTH - imgPlayer.getWidth() && key == 39) { //right arrow
                    player.setMoveRight(true);
                }

                if (player.getY() > 0 && key == 38) { //up arrow
                    player.setMoveUp(true);
                }
                if (player.getY() < GameView_HEIGHT - imgPlayer.getHeight() && key == 40) { //down arrow
                    player.setMoveDown(true);
                }
                if (key == 32) { //space
                    if (!player.isBullet()) {
                        player.setBullet(true);
                        shoot();
                    }
                }
            }

            //starts the game again if the game ended
            if(started && !ingame) {
                if (key == 10) { //enter
                    resetGame();
                    player.setAlive(true);
                    ingame = true;
                }
            }

            //exits the game
            if (key == 16) {
                System.exit(0);
            }
        }//end of keyPress event
    }//end of class TAdapter


    public void loadImages() {
        try {

            //loads logo image
            imgLogo = ImageIO.read(this.getClass().getResourceAsStream("/BxA Logo.png"));

            //loads player ship image
            imgPlayer = ImageIO.read(this.getClass().getResourceAsStream("/playerShip.png"));

            //loads enemy type 1 image
            imgAlienType1 = ImageIO.read(this.getClass().getResourceAsStream("/enemyType1.png"));

            //loads enemy type 2 image
            imgAlienType2 = ImageIO.read(this.getClass().getResourceAsStream("/enemyType2.png"));

            //loads enemy type 3 image
            imgAlienType3 = ImageIO.read(this.getClass().getResourceAsStream("/enemyType3.png"));

        } catch (Exception e) { e.printStackTrace(); }
    }//end of loadImages

    public void shoot() {

        //add a new bullet to the bullet list
        Bullet newBullet = new Bullet(player.getX() + 25, player.getY());
        bulletList.add(newBullet);

        //if a bullet is above the game screen, store a new bullet off screen
        for (int i = 0; i < bulletList.size(); i++) {
            Bullet bullet = bulletList.get(i);
            if (bullet.getY() < 0)
                bulletList.remove(bulletList.get(i));
        }
    }//end of shoot

    public void move(){

        //moves all bullets forward
        for(int i = 0; i < bulletList.size(); ++i)
            bulletList.set(i, new Bullet(bulletList.get(i).getX(), bulletList.get(i).getY() - 10));

        //moves all aliens down according to their moveSpeed
        for (int i = 0; i < aliens.length; i++)
            aliens[i].setY(aliens[i].getY() + aliens[i].getMoveSpeed());

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
                else if (i>=1)
                    i--;
            }
        }

        //check if any enemy ships have been hit by bullets
        for (int j = 0; j < aliens.length; j++) {

            //"eliminates" a hit enemy ship by creating a new one above the game screen and incrementing score
            if (aliens[j].isHit()) {
                playerScore += aliens[j].getScoreToDrop();
                createShip(j);
            }

            //creates a new enemy ship above the game screen if the given enemy ship travels below the game screen
            else if (aliens[j].getY() >= GameView_HEIGHT) {
                createShip(j);
            }
        }

        //if an enemy ship collides with the player,
        //  player loses a life and is set to the default position
        //  and a new enemy ship is created above the game screen
        for (int i = 0; i < aliens.length; i++) {
            if (aliens[i].getHitBox().intersects(player.getHitBox())) {
                createShip(i);
                player.setX(playerDefault.x);
                player.setY(playerDefault.y);
                player.decrementLives();

                //if the player dies, player's alive status is set to false,
                //  the game sends a leaderBoardRequest(), and sends a loginRequest()
                if (player.getLives() == 0) {
                    player.setX(-50);
                    player.setAlive(false);

                    //sends a leaderBoardRequest() to receive highscore data
                    Thread sendRequest = new Thread( () -> { leaderBoardRequest(playerName, playerScore); } );
                    sendRequest.start();
                    while (sendRequest.isAlive()) {
                        try {
                            sendRequest.join();
                        } catch(InterruptedException e) { e.printStackTrace(); }
                    }

                    //sends a profileRequest to update the player's displayed profile details
                    Thread sendName = new Thread( () -> { profileRequest(playerName); } );
                    sendName.start();
                    while (sendName.isAlive()) {
                        try {
                            sendName.join();
                        } catch(InterruptedException ex) {ex.printStackTrace();}
                    }
                }//end of inner if
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
        for(int i = 0; i < bulletList.size(); ++i)
            bulletList.set(i, new Bullet(0, 0));

        //resets player
        player = new PlayerShip((int)playerDefault.getX(), (int)playerDefault.getY());
        player.setX((int)playerDefault.getX());
        player.setY((int)playerDefault.getY());
        playerScore = 0;

        //resets enemy ships
        for(int i = 0; i < aliens.length; i++)
            createShip(i);

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


    public void profileRequest(String userName) {
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

    //mouse inputs are not used
    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {}

    public void run() {

        int animationDelay = 17;
        long time = System.currentTimeMillis();
        while (true) {//infinite loop

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

