package edu.sdccd.cisc191.b.client;

import edu.sdccd.cisc191.b.*;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

/**
 * @author Joaquin Dicang
 *
 * This program is a client used to send and receive data from the Server pertaining to User objects.
 *
 * When the game is launched, the player is prompted for a String userName. The Client
 * sends a UserProfileRequest(userName) to the Server in order to see if the User is
 * already in the database. The Client receives a UserProfileResponse containing User
 * information, and the Client creates a User using this information in
 * order to hold the player's data as they play the game.
 *
 * As the game is played, the player accumulates score for defeating enemies, which is
 * stored and updated in an instance variable Integer score. When the game is over (the
 * player ship collides with an enemy ship, or an enemy ship reaches the bottom of the
 * screen), the Client sends a UserScoreRequest(userName,score) to the Server. The Client
 * receives an ArrayList of UserScoreResponse elements, containing the top 10 Users with
 * the highest scores in the database. The Client displays the userName and highScore
 * of each UserScoreResponse.
 */

public class Client {
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public void startConnection(String ip, int port) throws Exception {
        clientSocket = new Socket(ip, port);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());

        /*
        out.writeObject(new UserProfileRequest("Azaxar"));
        UserProfileResponse response = (UserProfileResponse)in.readObject();
        User user = new User(response.getUserName(), response.getGamesPlayed(), response.getGameLevelsCleared(), response.getHighScore());
        System.out.println(response);
        */

        out.writeObject(new UserScoreRequest("Azaxar",20000));
        ArrayList<UserScoreResponse> responses = (ArrayList<UserScoreResponse>)in.readObject();
        for (UserScoreResponse response: responses)
            System.out.println(response);
    }


    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.startConnection("127.0.0.1", 4444);
            client.stopConnection();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
} //end class Client

