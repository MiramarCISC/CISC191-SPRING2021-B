package edu.sdccd.cisc191.b;

import java.io.Serializable;

/**
 * This class is used by the client to send profile requests to the server.
 *</p>
 * When the game is launched, the client prompts the user for their username.
 * When the user enters an appropriate name, a profile request is sent to the
 * server using the entered name in order to log in.
 *
 *
 * @author Joaquin Dicang
 */
public class UserProfileRequest implements Serializable {
    private String userName;

    protected UserProfileRequest() {}

    /**
     * @param userName Player's entered username, to either create a profile or log in to one
     */
    public UserProfileRequest(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() { return String.format("User[userName='%s']", userName); }

    public String getUserName() {
        return userName;
    }
}
