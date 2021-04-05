package edu.sdccd.cisc191.b;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The UserDataRequest class serves as a request in the form of a User's username.
 * The request is sent to the server to check if the username is in use; if not,
 * a set of data for a new user is created and sent back to the client.
 */

public class UserDataRequest {
    private String username;

    @JsonIgnore
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static String toJSON(UserDataRequest user) throws Exception {
        return objectMapper.writeValueAsString(user);
    }
    public static UserDataRequest fromJSON(String input) throws Exception{
        return objectMapper.readValue(input, UserDataRequest.class);
    }
    protected UserDataRequest() {}

    public UserDataRequest(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return String.format(
                "User: %s",
                username);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}