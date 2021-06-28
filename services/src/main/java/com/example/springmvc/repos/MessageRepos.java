package com.example.springmvc.repos;

import com.example.springmvc.dommain.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Message repo (Message in progress)
 */
@Repository
public interface MessageRepos extends CrudRepository<Message, Long> {
    List<Message> findByTag(String tag);
}
