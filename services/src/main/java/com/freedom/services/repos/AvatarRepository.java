package com.freedom.services.repos;

import com.freedom.services.dommain.Avatar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarRepository extends CrudRepository<Avatar,Long> {
}
