package edu.sdccd.cisc191.b.server;

import edu.sdccd.cisc191.b.CustomerRequest;
import edu.sdccd.cisc191.b.CustomerResponse;
import edu.sdccd.cisc191.b.UserDataRequest;
import edu.sdccd.cisc191.b.UserDataResponse;

import java.net.*;
import java.io.*;

/**
 * This program is a server that takes connection requests on
 * the port specified by the constant LISTENING_PORT.  When a
 * connection is opened, the program sends the current time to
 * the connected socket.  The program will continue to receive
 * and process connections until it is killed (by a CONTROL-C,
 * for example).  Note that this server processes each connection
 * as it is received, rather than creating a separate thread
 * to process the connection.
 */
public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * The server is used to create new Users if the username is not already in use.
     * It may also be used to store User data for subsequent uses (logins/logouts).
     * This version of Server for the lab creates a user without checking if the
     * username is in use.
     */

    public void start(int port) throws Exception {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            UserDataRequest request = UserDataRequest.fromJSON(inputLine);
            UserDataResponse response = new UserDataResponse(0, request.getUsername());
            out.println(UserDataResponse.toJSON(response));
        }
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.start(4444);
            server.stop();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
} //end class Server
