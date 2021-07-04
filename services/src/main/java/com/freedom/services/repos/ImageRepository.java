package com.freedom.services.repos;

import com.freedom.services.dommain.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ImageRepository extends CrudRepository<Image,Long> {

    List<Image> findAllByDeleteDateTimeBefore(LocalDateTime date);
    List<Image>  removeAllByDeleteDateTimeBefore(LocalDateTime date);
}
