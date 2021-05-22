package edu.sdccd.cisc191.b.client;

import edu.sdccd.cisc191.b.*;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

/**
 * This program opens a connection to a computer specified
 * as the first command-line argument.  If no command-line
 * argument is given, it prompts the user for a computer
 * to connect to.  The connection is made to
 * the port specified by LISTENING_PORT.  The program reads one
 * line of text from the connection and then closes the
 * connection.  It displays the text that it read on
 * standard output.  This program is meant to be used with
 * the server program, DateServer, which sends the current
 * date and time on the computer where the server is running.
 *
 * This client, beyond the above listed capabilities, is used to
 * send and receive data from the Server pertaining to User objects.
 * For the sake of the lab, a request is sent for the User with
 * username "Brent", and without checking for validity, data for
 * a User with the username Brent and a player level of 0 is
 * returned from the Server.
 */

public class Client {
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public void startConnection(String ip, int port) throws Exception {
        clientSocket = new Socket(ip, port);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());

        out.writeObject(new UserProfileRequest("Azaxar"));
        UserProfileResponse response = (UserProfileResponse)in.readObject();
        User user = new User(response.getUserName(), response.getGamesPlayed(), response.getGameLevelsCleared(), response.getHighScore());
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

