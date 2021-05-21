package edu.sdccd.cisc191.b;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserScoreRequest {
    private Integer score;

    /**
     * converts java objects to JSON objects
     */
    @JsonIgnore
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static String toJSON(UserScoreRequest user) throws Exception{
        return objectMapper.writeValueAsString(user);
    }

    /**
     * converts  JSON objects to java objects
     */
    public static UserScoreRequest fromJSON(String input) throws Exception{
        return objectMapper.readValue(input, UserScoreRequest.class);
    }
    protected UserScoreRequest() {}

    public UserScoreRequest(Integer score){
        this.score = score;
    }

    @Override
    public String toString(){
        return String.format("User[score=%d]", score);
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}