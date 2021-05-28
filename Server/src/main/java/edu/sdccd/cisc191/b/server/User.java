package edu.sdccd.cisc191.b.server;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * This User class defines the User Entities which fill the database holding user profiles
 * for our game. This class is available exclusively for Server functions.
 *
 * @author Joaquin Dicang
 */
@Entity(name = "User_Entity")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userName;
    private int gamesPlayed;
    private int gameLevelsCleared;
    private int highScore;

    protected User(){}

    /**
     * The User entity constructor is only used if the server needs to create a profile for a new user.
     * The only value required is a username provided by the player. All of the instance fields are set
     * to default values.
     *
     * @param name Player's username
     */
    public User(String name) {
        this.userName = name;
        gamesPlayed = 0;
        gameLevelsCleared = 0;
        highScore = 0;
    }

    public User(String name, int highScore) {
        this.userName = name;
        gamesPlayed = 0;
        gameLevelsCleared = 0;
        this.highScore = highScore;
    }

    @Override
    public String toString() {
        return String.format("User[id=%d, userName='%s', highScore=%d]", id, userName, highScore);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

}

