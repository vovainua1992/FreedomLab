package com.example.springmvc.service;

import com.example.springmvc.dommain.Publish;
import com.example.springmvc.dommain.User;
import com.example.springmvc.dommain.dto.PublishContent;
import com.example.springmvc.repos.PublicationRepos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PublicationService {
    private final PublicationRepos publicationRepos;

    public boolean delete(Publish publish,
                          User user) {
        if (publish.isEdit(user)) {
            publicationRepos.delete(publish);
            return true;
        } else
            return false;
    }

    public void updatePoster(Publish publish,String visible,String title){
        publish.setTitleNames(title);
        if (!visible.isEmpty())
            publish.setActive(true);
        else
            publish.setActive(false);
        publicationRepos.save(publish);
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
}
