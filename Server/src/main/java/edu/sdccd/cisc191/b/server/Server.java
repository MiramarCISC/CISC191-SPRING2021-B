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

import java.net.*;
import java.io.*;
import java.util.ArrayList;

/**
 * @author Joaquin Dicang
 *
 * This program is a server used to send and receive data from the Client pertaining to User objects.
 *
 * When the game is launched, the Server receives a UserProfileRequest(userName) from
 * the Client containing a String userName. The Server finds a User in the database
 * with userName, and a User does not exist, the Server creates a User with userName
 * and saves it to the database. The Server sends a UserProfileResponse containing the
 * found or created User information to the Client.
 *
 * As the game is played, the player accumulates score for defeating enemies, which is
 * stored and updated in an instance variable Integer score. When the game is over (the
 * player ship collides with an enemy ship, or an enemy ship reaches the bottom of the
 * screen), the Server receives a UserScoreRequest(userName,score) from the Client. The
 * Server finds a User using userName, and compares the score from the request to the
 * highScore in the database; if the score is higher, the User in the database is updated
 * with the higher score. The Server finds the top 10 Users with the highest scores, and
 * translates the List of User elements into an ArrayList of UserScoreResponse elements.
 * The Server sends the ArrayList to the Client.
 */
@SpringBootApplication
public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private static final Logger log = LoggerFactory.getLogger(Server.class);

    public void start(int port, UserRepository userRepository) throws Exception {
        serverSocket = new ServerSocket(port);

        while (true) {
            clientSocket = serverSocket.accept();

            new Thread(() -> {
                try {
                    out = new ObjectOutputStream(clientSocket.getOutputStream());
                    in = new ObjectInputStream(clientSocket.getInputStream());

                    Object requestObject = in.readObject();
                    if (requestObject instanceof UserProfileRequest) {
                        UserProfileRequest request = (UserProfileRequest) requestObject;
                        log.info("");
                        log.info("UserProfileRequest received: " + request);
                        User user = userRepository.findByUserName(request.getUserName());
                        if (user == null) {
                            user = new User(request.getUserName());
                            userRepository.save(user);
                        }
                        UserProfileResponse response = new UserProfileResponse(user.getUserName(), user.getGamesPlayed(), user.getHighScore());
                        out.writeObject(response);
                        log.info("UserProfileResponse sent: " + response);
                    }
                    else if (requestObject instanceof UserScoreRequest) {
                        UserScoreRequest request = (UserScoreRequest) requestObject;
                        log.info("");
                        log.info("UserScoreRequest received: " + request);
                        User user = userRepository.findByUserName(request.getUserName());
                        if (request.getHighScore() > user.getHighScore()) {
                            user.setHighScore(request.getHighScore());
                        }
                        user.setGamesPlayed(user.getGamesPlayed() + 1);
                        userRepository.save(user);
                        ArrayList<UserScoreResponse> leaderBoard = new ArrayList<>();
                        for (User u : userRepository.findTop10ByOrderByHighScoreDesc()) {
                            leaderBoard.add(new UserScoreResponse(u.getUserName(), u.getHighScore()));
                        }
                        out.writeObject(leaderBoard);
                        for (UserScoreResponse r : leaderBoard) log.info("UserScoreResponse sent: " + r);
                    }

                } catch (Exception e) { e.printStackTrace(); }
            }).start();
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
