package edu.sdccd.cisc191.b.server;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public interface UserRepository extends CrudRepository<User, Long>{

    User findByUserName(String userName);

    User findById(long id);

    List<User> findTop10ByOrderByHighScoreDesc();

    @Modifying
    @Query("update User_Entity u set u.highScore = :highScore where u.userName = :userName")
    void updateHighScore(String userName, Integer highScore);
}
