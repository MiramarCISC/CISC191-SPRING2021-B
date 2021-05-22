package edu.sdccd.cisc191.b;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserProfileResponse {
    private String userName;
    private Integer gamesPlayed;
    private Integer gameLevelsCleared;
    private Integer highScore;

    @JsonIgnore
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static String toJSON(UserProfileResponse profile) throws Exception {
        return objectMapper.writeValueAsString(profile);
    }
    public static UserProfileResponse fromJSON(String input) throws Exception{
        return objectMapper.readValue(input, UserProfileResponse.class);
    }
    protected UserProfileResponse() {}

    public UserProfileResponse(String name, Integer gamesPlayed, Integer gameLevelsCleared, Integer highScore) {
        this.userName = name;
        this.gamesPlayed = gamesPlayed;
        this.gameLevelsCleared = gameLevelsCleared;
        this.highScore = highScore;
    }

    @Override
    public String toString() {
        return String.format(
                "User[name='%s', gamesPlayed=%d, gameLevelsCleared=%d, highScore=%d]",
                userName,gamesPlayed,gameLevelsCleared,highScore);
    }

}
