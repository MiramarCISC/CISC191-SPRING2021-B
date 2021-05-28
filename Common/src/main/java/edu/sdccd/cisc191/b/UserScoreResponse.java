package edu.sdccd.cisc191.b;

import java.io.Serializable;

/**
 * This class is used by the server to send score responses to the client.
 *</p>
 * When the server is done updating the player's profile with relevant
 * information, the server generates a list of the top 10 profiles, and creates
 * a list of profile responses to send back to the client.
 *
 * @author Joaquin Dicang
 */
public class UserScoreResponse implements Serializable {
    private String userName;
    private Integer highScore;

    protected UserScoreResponse() {}

    /**
     * @param userName Returned profile's username
     * @param highScore Returned profile's highscore
     */
    public UserScoreResponse(String userName, Integer highScore) {
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
