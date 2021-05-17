package com.example.springmvc.repos;

import com.example.springmvc.dommain.User;
import com.example.springmvc.dommain.Publish;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PublicationRepos extends CrudRepository<Publish,Long> {

    List<Publish> findAllByActiveTrue();

    Publish findById(long id);

    List<Publish> findAllByAuthor(User author);
}
