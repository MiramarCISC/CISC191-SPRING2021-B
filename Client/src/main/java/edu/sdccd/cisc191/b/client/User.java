package edu.sdccd.cisc191.b.client;

/**
 * @author Joaquin Dicang
 *
 * The User class defines User objects to be used as profiles for our game.
 * This class is available exclusively for client functions.
 */
public class User{
    private String userName;
    private int gamesPlayed;
    private int highScore;

    /**
     * The User constructor is used by the client to create a User for the player based on
     * profile information returned from the server.
     *
     * @param name Player's username
     * @param gamesPlayed Player's amount of games played
     * @param highScore Player's highest score
     */
    public User(String name, int gamesPlayed, int highScore) {
        this.userName = name;
        this.gamesPlayed = gamesPlayed;
        this.highScore = highScore;
    }

    public String toString() { return String.format("User: %s   |   Games Played: %d   |   HighScore: %d", userName, gamesPlayed, highScore); }
}