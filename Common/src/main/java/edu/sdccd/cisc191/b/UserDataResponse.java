package edu.sdccd.cisc191.b;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The UserDataResponse class takes a User's inputted username and
 * creates a base profile. For the lab example, the server will return
 * the String, though in the final version, the client will receive the
 * response object and construct a User object, which will be added to
 * an ArrayList of Users if the username is not already in use.
 */

public class UserDataResponse {
    private Integer playerLevel;
    private String username;

    @JsonIgnore
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static String toJSON(UserDataResponse user) throws Exception {
        return objectMapper.writeValueAsString(user);
    }
    public static UserDataResponse fromJSON(String input) throws Exception{
        return objectMapper.readValue(input, UserDataResponse.class);
    }
    protected UserDataResponse() {}

    public UserDataResponse(Integer playerLevel, String username) {
        this.playerLevel = playerLevel;
        this.username = username;
    }

    @Override
    public String toString() {
        return String.format(
                "User: %s, player level %d",
                username,playerLevel);
    }

    public Integer getPlayerLevel() {
        return playerLevel;
    }

    public String getUsername() {
        return username;
    }

    public void setPlayerLevel(Integer playerLevel) {
        this.playerLevel = playerLevel;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}