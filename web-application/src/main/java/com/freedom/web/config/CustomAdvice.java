package com.freedom.web.config;

import com.freedom.services.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class CustomAdvice {
    private final CategoryService categoryService;

    @ModelAttribute
    public void addAllCategory(Model model){
        model.addAttribute("categories",categoryService.getAll());
    }
}
