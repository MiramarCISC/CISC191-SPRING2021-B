package edu.sdccd.cisc191.b;

import java.io.Serializable;

public class UserScoreRequest implements Serializable {
    private String userName;
    private Integer highScore;

    protected UserScoreRequest() {}

    public UserScoreRequest(String userName, Integer highScore) {
        this.userName = userName;
        this.highScore = highScore;
    }

    @Override
    public String toString() {
        return String.format(
                "User[userName='%s', highScore=%d]",
                userName,highScore);
    }

    public String getUserName() {
        return userName;
    }

    public Integer getHighScore() {
        return highScore;
    }
}
