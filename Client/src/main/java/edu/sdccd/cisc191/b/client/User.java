package edu.sdccd.cisc191.b.client;

import java.util.*;

public class User implements Comparable<User>{
    private String name;
    private int userXPLevel;
    private int userLevel;
    private int gamesPlayed;
    private int gameLevelsCleared;
    private int highScore;

    public User(String name) {
        this.name = name;
        userLevel = 0;
        userXPLevel = 0;
        gamesPlayed = 0;
        gameLevelsCleared = 0;
        highScore = 0;
    }

    public User(String name, int userLevel) {
        this.name = name;
        this.userLevel = userLevel;
        userXPLevel = 0;
        gamesPlayed = 0;
        gameLevelsCleared = 0;
        highScore = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserXPLevel() {
        return userXPLevel;
    }

    public void setUserXPLevel(int userXPLevel) {
        this.userXPLevel = userXPLevel;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
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
        int compVal = 0;
        if (high == thisHigh) {
            int level = user.getUserLevel();
            int thisLevel = this.getUserLevel();
            if (level == thisLevel) {
                String name = user.getName();
                String thisName = this.getName();
                compVal = thisName.compareTo(name);
            }
            else if(level > thisLevel)
                compVal = -1;
            else if(level < thisLevel)
                compVal = 1;
        }
        else if(high > thisHigh)
            compVal = -1;
        else if(high < thisHigh)
            compVal = 1;
        return compVal;
    }
}
