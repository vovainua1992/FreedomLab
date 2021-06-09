package com.example.springmvc.repos;

import com.example.springmvc.dommain.Avatar;
import org.springframework.data.repository.CrudRepository;

public interface AvatarRepos extends CrudRepository<Avatar,Long> {
}
