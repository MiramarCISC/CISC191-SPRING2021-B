package edu.sdccd.cisc191.b;

import java.io.Serializable;

/**
 * @author Joaquin Dicang
 *
 * This class is used by the server to send profile responses to the client.
 *
 * After creating or receiving profile data from the database, the server sends
 * a response containing that profile data back to the client.
 */
public class UserProfileResponse implements Serializable {
    private String userName;
    private Integer gamesPlayed;
    private Integer highScore;

    protected UserProfileResponse() {}

    /**
     * @param name Returned profile's name
     * @param gamesPlayed Returned profile's amount of games played
     * @param highScore Returned profile's highest score after a game
     */
    public UserProfileResponse(String name, Integer gamesPlayed, Integer highScore) {
        this.userName = name;
        this.gamesPlayed = gamesPlayed;
        this.highScore = highScore;
    }

    @Override
    public String toString() { return String.format("User[name='%s', gamesPlayed=%d, highScore=%d]", userName, gamesPlayed, highScore);
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
