package com.freedom.services.dommain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "images")
@ToString(includeFieldNames = true)
public class Image implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "url")
    private String url;
    @Column(name = "description")
    private String description;
    @Column(name = "upload_date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime uploadDateTime;
    @Column(name = "delete_date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime deleteDateTime;

    public static Image newImage(String name, String url, LocalDateTime load_time) {
        Image image = new Image();
        image.setName(name);
        image.setUrl(url);
        image.setUploadDateTime(load_time);
        image.setDescription("Опис...");
        return image;
    }
}
