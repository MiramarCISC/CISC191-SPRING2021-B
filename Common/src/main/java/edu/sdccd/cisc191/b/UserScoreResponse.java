package edu.sdccd.cisc191.b;

import java.io.Serializable;

public class UserScoreResponse implements Serializable {
    private String userName;
    private Integer highScore;

    protected UserScoreResponse() {}

    public UserScoreResponse(String userName, Integer highScore) {
        this.userName = userName;
        this.highScore = highScore;
    }

    @Override
    public String toString() { return String.format("User[userName='%s', highScore=%d]", userName, highScore); }

    public String getUserName() {
        return userName;
    }

    public Integer getHighScore() {
        return highScore;
    }
}
