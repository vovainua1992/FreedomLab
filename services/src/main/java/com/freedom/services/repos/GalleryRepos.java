package com.freedom.services.repos;

import com.freedom.services.dommain.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepos extends JpaRepository<Gallery,Long> {
}
