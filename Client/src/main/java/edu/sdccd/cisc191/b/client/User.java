package edu.sdccd.cisc191.b.client;

import java.util.*;

public class User implements Comparable<User>{
    private String name;
    private int gamesPlayed;
    private int gameLevelsCleared;
    private int highScore;

    public User(String name) {
        this.name = name;
        gamesPlayed = 0;
        gameLevelsCleared = 0;
        highScore = 0;
    }

    public User(String name, int highScore) {
        this.name = name;
        gamesPlayed = 0;
        gameLevelsCleared = 0;
        this.highScore = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
            return this.name.compareTo(user.name);
    }
}
