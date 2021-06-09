package com.example.springmvc.repos;

import com.example.springmvc.dommain.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ImageRepository extends CrudRepository<Image,Long> {

    List<Image> findAllByDeleteDateTimeBefore(LocalDateTime date);
    List<Image>  removeAllByDeleteDateTimeBefore(LocalDateTime date);
}
