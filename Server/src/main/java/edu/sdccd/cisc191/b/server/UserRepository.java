package edu.sdccd.cisc191.b.server;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>{

    User findByUserName(String userName);

    User findById(long id);

    List<User> findTop10ByOrderByHighScoreDesc();

}
