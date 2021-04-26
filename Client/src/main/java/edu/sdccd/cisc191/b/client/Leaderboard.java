package edu.sdccd.cisc191.b.client;

import java.util.*;

public class Leaderboard {
    private ArrayList<User> levelBoard;
    private ArrayList<User> scoreBoard;
    int bruh;

    public Leaderboard(){
        ArrayList<User> leaderboard = new ArrayList<User>();
        leaderboard.add(new User("James"));
        leaderboard.add(new User("Ronda", 15));
        leaderboard.add(new User("Mary", 7));
        leaderboard.add(new User("Marco", 11));

        int total = 0;
        for (User user: leaderboard) {
            total += user.getUserLevel();
        }
        double avg = total/leaderboard.size();

    }
}
