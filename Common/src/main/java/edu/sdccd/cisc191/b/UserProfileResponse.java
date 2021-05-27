package edu.sdccd.cisc191.b;

import java.io.Serializable;

public class UserProfileResponse implements Serializable {
    private String userName;
    private Integer gamesPlayed;
    private Integer highScore;

    protected UserProfileResponse() {}

    public UserProfileResponse(String name, Integer gamesPlayed, Integer highScore) {
        this.userName = name;
        this.gamesPlayed = gamesPlayed;
        this.highScore = highScore;
    }

    @Override
    public String toString() {
        return String.format(
                "User[name='%s', gamesPlayed=%d, highScore=%d]",
                userName,gamesPlayed,highScore);
    }

    public String getUserName() {
        return userName;
    }

    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    public Integer getHighScore() {
        return highScore;
    }
}
