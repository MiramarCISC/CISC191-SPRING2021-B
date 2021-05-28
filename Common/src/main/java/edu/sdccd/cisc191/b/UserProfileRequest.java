package edu.sdccd.cisc191.b;

import java.io.Serializable;

public class UserProfileRequest implements Serializable {
    private String userName;

    protected UserProfileRequest() {}

    public UserProfileRequest(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() { return String.format("User[userName='%s']", userName); }

    public String getUserName() {
        return userName;
    }
}
