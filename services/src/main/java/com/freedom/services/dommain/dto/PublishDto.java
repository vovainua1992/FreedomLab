package com.freedom.services.dommain.dto;

import com.freedom.services.dommain.Category;
import com.freedom.services.dommain.Image;
import com.freedom.services.dommain.Publish;
import com.freedom.services.dommain.User;
import com.freedom.services.dommain.enums.PublishType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class PublishDto {
    private Long id;
    private boolean active;
    private Image image;
    private String titleNames;
    private String textHtml;
    private User author;
    private PublishType type;
    private Category category;
    private LocalDateTime dateCreate;
    private LocalDateTime datePublication;
    private long likes ;
    private boolean meLiked;


    public PublishDto(Publish publish, Long likes, Boolean meLiked) {
        this.id = publish.getId();
        this.active = publish.isActive();
        this.image = publish.getImage();
        this.titleNames = publish.getTitleNames();
        this.textHtml = publish.getTextHtml();
        this.author = publish.getAuthor();
        this.type = publish.getType();
        this.category = publish.getCategory();
        this.dateCreate = publish.getDateCreate();
        this.datePublication = publish.getDatePublication();
        this.likes = likes;
        this.meLiked = meLiked;
    }

    public boolean isEdit(User user) {
        return user != null && (user.equals(getAuthor()) || user.isAdmin());
    }

    public boolean isAuthor(User user) {
        return author.equals(user);
    }

    public String getImageUrl(){
        if (image!=null)
            return image.getUrl();
        else
            return "/static/icons/image.svg";
    }

    public String dateToString(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return localDateTime.format(formatter);
    }

}
