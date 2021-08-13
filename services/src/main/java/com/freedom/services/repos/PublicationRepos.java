package com.freedom.services.repos;

import com.freedom.services.dommain.Publish;
import com.freedom.services.dommain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Publish controller
 */
@Repository
public interface PublicationRepos extends CrudRepository<Publish, Long> {
    @Query(value = "SELECT * FROM tuto.public.publishes p WHERE p.type='CUSTOM' and p.active=true",nativeQuery = true)
    Page<Publish> findAllByActiveTrueAndTypeEqualsCustom(Pageable pageable);

    Publish findById(long id);

    Page<Publish> findAllByAuthor(User author,Pageable pageable);

    List<Publish> findAllByAuthor(User user);
}
