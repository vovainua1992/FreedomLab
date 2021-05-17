package com.example.springmvc.repos;

import com.example.springmvc.dommain.User;
import com.example.springmvc.dommain.Publish;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Publish controller
 */
@Repository
public interface PublicationRepos extends CrudRepository<Publish,Long> {

    List<Publish> findAllByActiveTrue();

    Publish findById(long id);

    List<Publish> findAllByAuthor(User author);
}
