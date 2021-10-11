package com.freedom.services.service;

import com.freedom.services.dommain.Category;
import com.freedom.services.repos.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAll(){
        return  categoryRepository.findAll();
    }

    public boolean add(String name){
        Category categoryFromDb = categoryRepository.findByName(name);
        if (categoryFromDb!=null)
            return false;
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);
        return true;
    }


    public Category getByID(Long categoryId) {
        return categoryRepository.findById(categoryId).get();
    }
}
