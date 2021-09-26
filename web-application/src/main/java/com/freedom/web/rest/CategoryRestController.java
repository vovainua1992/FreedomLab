package com.freedom.web.rest;

import com.freedom.services.dommain.Category;
import com.freedom.services.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/categories")
@RequiredArgsConstructor
public class CategoryRestController {
    private final CategoryService categoryService;

    @PostMapping("/add")
    public String addCategory(@RequestParam(name = "name_category")String name){
        if(categoryService.add(name))
            return "true";
        else
            return "false";
    }

    @GetMapping("/get-all")
    public List<Category> getAll(){
        return categoryService.getAll();
    }

}
