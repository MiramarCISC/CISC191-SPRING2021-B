package edu.sdccd.cisc191.b.server;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getGameLevelsCleared() {
        return gameLevelsCleared;
    }

    public void setGameLevelsCleared(int gameLevelsCleared) {
        this.gameLevelsCleared = gameLevelsCleared;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

}

