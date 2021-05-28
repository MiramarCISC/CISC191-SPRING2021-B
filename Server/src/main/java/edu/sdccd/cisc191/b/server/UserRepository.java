package edu.sdccd.cisc191.b.server;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>{

    User findByUserName(String userName);

    List<User> findTop10ByOrderByHighScoreDesc();

}
