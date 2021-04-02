package edu.sdccd.cisc191.b;

public class User {
    private String name;
    private int userXPLevel;
    private int userLevel;
    private int gamesPlayed;
    private int gameLevelsCleared;
    private int highScore;

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
}
