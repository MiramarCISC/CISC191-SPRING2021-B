package edu.sdccd.cisc191.b.client;

/**
 * The User class defines User objects to be used as "profiles" for our game.
 *
 * @author Joaquin Dicang
 */

public class User implements Comparable<User>{
    private String userName;
    private int gamesPlayed;
    private int highScore;

    public User(String name) {
        this.userName = name;
        gamesPlayed = 0;
        highScore = 0;
    }

    public User(String name, int highScore) {
        this.userName = name;
        gamesPlayed = 0;
        this.highScore = highScore;
    }

    public User(String name, int gamesPlayed, int highScore) {
        this.userName = name;
        this.gamesPlayed = gamesPlayed;
        this.highScore = highScore;
    }

    /**
     *Accesses user's username.
     *
     * @return the username
     */
    public String getUserName() {
        return userName;
    }
/**
 * Sets the total number of games played by the user.
 */
    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    /**
     * Accesses users high score.
     *
     * @return the high score value
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Sets the user's high score.
     *
     * @param highScore the new high score value
     */
    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    /**
     * Compares two users' high scores.
     *
     * @param user the user profile
     * @return the integer value after the comparison
     */
    @Override
    public int compareTo(User user) {
        int high = user.getHighScore();
        int thisHigh = this.getHighScore();
        if (thisHigh > high)
            return 1;
        else if (thisHigh < high)
            return -1;
        else
            return this.userName.compareTo(user.userName);
    }
}
