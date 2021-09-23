package com.codeup.capstone.repositories;

import com.codeup.capstone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // Need to be able to search for user by username, as in Integration Tests
    User findByUserName(String userName);

    User findById(long id);

    //    @Query("FROM User u WHERE u.fullName LIKE %:term%")
    @Query("SELECT u FROM User u WHERE  u.fullName LIKE %?1%"
            + "OR u.userName LIKE %?1%"
            + "OR u.email LIKE %?1%"
    )
    List<User> findByUserNameLike(@Param("userTerm") String term);


}