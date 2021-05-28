package edu.sdccd.cisc191.b.client;

/**
 * The User class defines User objects to be used as "profiles" for our game.
 *
 * @author Joaquin Dicang
 */

public class User{
    private String userName;
    private int gamesPlayed;
    private int highScore;

    public User(String name, int gamesPlayed, int highScore) {
        this.userName = name;
        this.gamesPlayed = gamesPlayed;
        this.highScore = highScore;
    }

    public String toString() { return String.format("User: %s   |   Games Played: %d   |   HighScore: %d", userName, gamesPlayed, highScore); }
}
