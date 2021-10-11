package com.freedom.services.repos;

import com.freedom.services.dommain.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Long> {

    public List<Category> findAll();

    public Category findByName(String name);

}
