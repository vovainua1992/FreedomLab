package com.freedom.services.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedom.services.config.ServiceTestConfig;
import com.freedom.services.config.TestServiceApplication;
import com.freedom.services.dommain.Category;
import com.freedom.services.dommain.Publish;
import com.freedom.services.dommain.User;
import com.freedom.services.dommain.dto.PublishContent;
import com.freedom.services.dommain.dto.PublishDto;
import com.freedom.services.dommain.dto.Region;
import com.freedom.services.repos.CategoryRepository;
import com.freedom.services.repos.PublicationRepos;
import com.freedom.services.repos.UserRepos;
import lombok.extern.java.Log;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {TestServiceApplication.class, ServiceTestConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Log
class PublicationServiceTest {
    @Autowired
    private PublicationService publicationService;
    @Autowired
    private PublicationRepos publicationRepos;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepos userRepos;
    @Autowired
    private CategoryRepository categoryRepository;

    private User user;
    private User admin;

    @Test
    @Order(0)
    public void init() {
        List<Category> all = categoryRepository.findAll();
        assertEquals(all.size(),15);
    }

    @Test
    @Order(1)
    public void createPublish() {
        publicationService.createPublish(findUser(), "Test create", 1L);
        List<Publish> publishes = publicationRepos.findAll();
        assertEquals(publishes.size(), 1);
    }

    @Test
    @Order(2)
    public void delete() {
        publicationService.delete(publicationRepos.findById(1L),findUser());
        List<Publish> publishes = publicationRepos.findAll();
        assertEquals(publishes.size(), 0);
    }

    @Test
    @Order(3)
    public void updateContent() throws JsonProcessingException {
        publicationService.createPublish(findUser(), "test update content", 2L);
        Publish fromDB = publicationRepos.findById(2);
        assertNotNull(fromDB);
        PublishContent publishContent = new PublishContent();
        publishContent.setTitle("teeest");
        Set<Region> regions = new HashSet<>();
        Region region = new Region();
        region.setName("test");
        region.setRegion("New content publish");
        regions.add(region);
        publishContent.setRegions(regions);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(publishContent);
        publicationService.updateContent(json, fromDB);
    }

    @Test
    @Order(4)
    public void publication() {
        try {
            publicationService.publication(2,"test frame",new MockMultipartFile("name", Files.readAllBytes(Paths.get("src/test/resources/welcome.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(publicationRepos.findAllForUser(findUser()).size(),1);
    }

    @Test
    @Order(5)
    public void filteredPublish() {
        log.severe("Testing filtered in controller ...");
    }

    @Test
    @Order(6)
    public void getPublishForEditing() {
        PublishDto editing = publicationService.getPublishForEditing(2, findUser());
        assertNotNull(editing);
        PublishDto editingForAdmin = publicationService.getPublishForEditing(2,findAdmin());
        assertNotNull(editingForAdmin);
    }

    @Test
    @Order(7)
    public void inverseActivePublish() {
        publicationService.inverseActivePublish(2,findUser());
        Publish byId = publicationRepos.findById(2);
        assertFalse(byId.isActive());
    }

    @Test
    @Order(8)
    public void removeAuthor() {
        publicationService.removeAuthor(2);
       assertEquals(publicationRepos.findAllByAuthor(findUser()).size(),0);
    }

    private User findUser() {
        user = userRepos.findByUsername("tester");
        return user;
    }

    private User findAdmin() {
        admin = userRepos.findByUsername("okabe");
        return admin;
    }
}