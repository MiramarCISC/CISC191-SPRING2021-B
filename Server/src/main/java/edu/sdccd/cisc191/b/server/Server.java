package edu.sdccd.cisc191.b.server;

import edu.sdccd.cisc191.b.UserProfileRequest;
import edu.sdccd.cisc191.b.UserProfileResponse;
import edu.sdccd.cisc191.b.UserScoreRequest;
import edu.sdccd.cisc191.b.UserScoreResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
@SpringBootApplication
public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private static final Logger log = LoggerFactory.getLogger(Server.class);

    /**
     * The server is used to create new Users if the username is not already in use.
     * It may also be used to store User data for subsequent uses (logins/logouts).
     * This version of Server for the lab creates a user without checking if the
     * username is currently in use.
     */

    public void start(int port, UserRepository userRepository) throws Exception {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());

        Object requestObject = in.readObject();
        if (requestObject instanceof UserProfileRequest) {
            UserProfileRequest request = (UserProfileRequest) requestObject;
            User user = userRepository.findByUserName(request.getUserName());
            if (user == null) {
                user = new User(request.getUserName());
                userRepository.save(user);
            }
            UserProfileResponse response = new UserProfileResponse(user.getUserName(), user.getGamesPlayed(), user.getGameLevelsCleared(), user.getHighScore());
            out.writeObject(response);
        }
        else if (requestObject instanceof UserScoreRequest) {
            UserScoreRequest request = (UserScoreRequest) requestObject;
            User user = userRepository.findByUserName(request.getUserName());
            if (request.getHighScore() > user.getHighScore()) {
                user.setHighScore(request.getHighScore());
                userRepository.save(user);
            }
            ArrayList<UserScoreRequest> leaderBoard = new ArrayList<>();
            for (User u : userRepository.findTop10ByOrderByHighScoreDesc()) {
                leaderBoard.add(new UserScoreRequest(u.getUserName(), u.getHighScore()));
            }
            out.writeObject(leaderBoard);
        }
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }


    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }

    @Bean
    public CommandLineRunner startServer(UserRepository userRepository){
        return (args) -> {
            Server server = new Server();
            try {
                server.start(4444, userRepository);
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }

        };
    }
} //end class Server
