package com.freedom.services.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedom.services.dommain.Image;
import com.freedom.services.dommain.Publish;
import com.freedom.services.dommain.User;
import com.freedom.services.dommain.dto.PublishContent;
import com.freedom.services.repos.PublicationRepos;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

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

    public Publish updatePoster(long id, String visible, String title, MultipartFile imageTitle) throws IOException {
        Publish publish = publicationRepos.findById(id);
        if (!visible.isEmpty())
            publish.setActive(true);
        else
            publish.setActive(false);
        publish.setTitleNames(title);
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
        publishContent.getRegions().forEach((r) -> {
            System.out.println(r.toString());
        });
        publish.setTextHtml(publishContent.getHtml());
        publicationRepos.save(publish);
    }

    public Publish createPublishForAuthor(User user) {
        Publish publish =Publish.newForAuthor(user);
        publicationRepos.save(publish);
        return publish;
    }

    public Publish getPublishForEditing(long id, User user) {
        Publish publish = publicationRepos.findById(id);
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
}
