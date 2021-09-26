package com.freedom.services.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedom.services.dommain.Image;
import com.freedom.services.dommain.Publish;
import com.freedom.services.dommain.User;
import com.freedom.services.dommain.dto.PublishContent;
import com.freedom.services.dommain.dto.PublishDto;
import com.freedom.services.repos.PublicationRepos;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PublicationService {
    private final PublicationRepos publicationRepos;
    private final ImageService imageService;


    public boolean delete(Publish publish,
                          User user) {
        if (publish.isEdit(user)) {
            publicationRepos.delete(publish);
            return true;
        } else
            return false;
    }

    public Publish publication(long id, String title, MultipartFile imageTitle) throws IOException {
        Publish publish = publicationRepos.findById(id);
        publish.setActive(true);
        publish.setTitleNames(title);
        publish.setDatePublication(LocalDateTime.now());
        if (!imageTitle.isEmpty()) {
            Image image = imageService.saveImage(imageTitle);
            publish.setImage(image);
        }
        publicationRepos.save(publish);
        return publish;
    }

    public void updateContent(String json, Publish publish) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        PublishContent publishContent = objectMapper.readValue(json, PublishContent.class);
        publish.setTextHtml(publishContent.getHtml());
        publicationRepos.save(publish);
    }

    public PublishDto createPublish(User user, String name) {
        Publish publish = Publish.newPublishByAuthorAndName(user,name);
        Publish fromDb = publicationRepos.save(publish);
        return publicationRepos.findById(fromDb.getId(),user);
    }

    public PublishDto getPublishForEditing(long id, User user) {
        PublishDto publish = publicationRepos.findById(id,user);
        if (publish.isEdit(user))
            return publish;
        else
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
    }

    public void inverseActivePublish(long id, User user) {
        Publish publish = publicationRepos.findById(id);
        if (publish.isEdit(user)) {
            publish.setActive(!publish.isActive());
            publicationRepos.save(publish);
        }
    }

    public void removeAuthor(User removeAuthor,User deletedSystemUser) {
        List<PublishDto> publishes = publicationRepos.findAllByAuthor(removeAuthor);
        publishes.forEach((PublishDto p)->{p.setAuthor(deletedSystemUser);});
    }
}
