package edu.sdccd.cisc191.b.server;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * This interface defines the main search methods used by the server to search the database.
 * @author Joaquin Dicang
 */
public interface UserRepository extends CrudRepository<User, Long>{

    User findByUserName(String userName);

    List<User> findTop10ByOrderByHighScoreDesc();
}
