package edu.sdccd.cisc191.b;

import java.io.Serializable;

/**
 * This class is used by the client to send score requests to the server.
 *</p>
 * When the player loses all three lives and the game ends, the client takes
 * their username and final score and sends a score request to the server.
 *
 * @author Joaquin Dicang
 */
public class UserScoreRequest implements Serializable {
    private String userName;
    private Integer highScore;

    protected UserScoreRequest() {}

    /**
     * @param userName Player's username
     * @param highScore Player's score at the end of the game
     */
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