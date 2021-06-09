package com.example.springmvc.repos;

import com.example.springmvc.dommain.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepos extends JpaRepository<Gallery,Long> {
}
