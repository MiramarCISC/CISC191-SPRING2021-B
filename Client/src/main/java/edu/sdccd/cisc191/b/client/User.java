package edu.sdccd.cisc191.b.client;

import java.util.*;

public class User implements Comparable<User>{
    private String userName;
    private int gamesPlayed;
    private int gameLevelsCleared;
    private int highScore;

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

    public User(String name, int gamesPlayed, int gameLevelsCleared, int highScore) {
        this.userName = name;
        this.gamesPlayed = gamesPlayed;
        this.gameLevelsCleared = gameLevelsCleared;
        this.highScore = highScore;
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

    @Override
    public int compareTo(User user) {
        int high = user.getHighScore();
        int thisHigh = this.getHighScore();
        if (thisHigh > high)
            return 1;
        else if (thisHigh < high)
            return -1;
        else
            return this.userName.compareTo(user.userName);
    }
}
