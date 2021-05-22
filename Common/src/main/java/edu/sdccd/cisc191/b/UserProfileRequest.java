package edu.sdccd.cisc191.b;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserProfileRequest {
    private String userName;

    @JsonIgnore
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static String toJSON(UserProfileRequest profile) throws Exception {
        return objectMapper.writeValueAsString(profile);
    }
    public static UserProfileRequest fromJSON(String input) throws Exception{
        return objectMapper.readValue(input, UserProfileRequest.class);
    }
    protected UserProfileRequest() {}

    public UserProfileRequest(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return String.format(
                "User[userName='%s']",
                userName);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
