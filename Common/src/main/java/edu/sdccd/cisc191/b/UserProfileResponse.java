package edu.sdccd.cisc191.b;

import java.io.Serializable;

public class UserProfileResponse implements Serializable {
    private String userName;
    private Integer gamesPlayed;
    private Integer gameLevelsCleared;
    private Integer highScore;

    protected UserProfileResponse() {}

    public UserProfileResponse(String name, Integer gamesPlayed, Integer gameLevelsCleared, Integer highScore) {
        this.userName = name;
        this.gamesPlayed = gamesPlayed;
        this.gameLevelsCleared = gameLevelsCleared;
        this.highScore = highScore;
    }

    @Override
    public String toString() {
        return String.format(
                "User[name='%s', gamesPlayed=%d, gameLevelsCleared=%d, highScore=%d]",
                userName,gamesPlayed,gameLevelsCleared,highScore);
    }

    public String getUserName() {
        return userName;
    }

    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    public Integer getGameLevelsCleared() {
        return gameLevelsCleared;
    }

    public Integer getHighScore() {
        return highScore;
    }
}
