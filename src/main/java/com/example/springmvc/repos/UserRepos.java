package com.example.springmvc.repos;

import com.example.springmvc.dommain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User repository
 */
@Repository
public interface UserRepos extends JpaRepository<User,Long> {
    User findByUsername(String username);

    User findByActivationCode(String code);

    void deleteById(long id);
}
