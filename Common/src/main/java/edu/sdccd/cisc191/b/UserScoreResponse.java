package edu.sdccd.cisc191.b;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserScoreResponse {
    private Integer score;
    private String firstName;
    private String lastName;

    /**
     * converts java objects to JSON strings
     */
    @JsonIgnore
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static String toJSON(UserScoreResponse user) throws Exception{
        return objectMapper.writeValueAsString(user);
    }

    /**
     * converts  JSON strings to java objects
     */
    public static UserScoreResponse fromJSON (String input) throws Exception{
        return objectMapper.readValue(input, UserScoreResponse.class);
    }

    protected UserScoreResponse() {}

    public UserScoreResponse (Integer score, String firstName, String lastName){
        this.score = score;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * formats data into
             Name:
             Score:
     * @return
     */
    @Override
    public String toString() {
        return String.format(
                "Name: %s %s \nscore: %d",
                firstName, lastName, score);
    }

    public Integer getScore() {
        return score;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
