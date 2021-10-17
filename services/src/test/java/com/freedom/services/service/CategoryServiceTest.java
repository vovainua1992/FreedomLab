package com.freedom.services.service;

import com.freedom.services.config.ServiceTestConfig;
import com.freedom.services.config.TestServiceApplication;
import com.freedom.services.repos.CategoryRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes={TestServiceApplication.class, ServiceTestConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Order(1)
    void getAll() {
        assertEquals(categoryService.getAll().size(),15);
    }

    @Test
    @Order(2)
    void add() {
        categoryService.add("test");
        assertEquals(categoryService.getAll().size(),16);
        assertNotNull(categoryRepository.findByName("test"));
    }

    @Test
    @Order(3)
    void getByID() {
        assertEquals(categoryService.getByID(16L).getName(),"test");
    }
}