package com.freedom.services.dommain;

import com.freedom.services.dommain.enums.PublishType;
import com.freedom.services.dommain.enums.PublishesCategory;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Publish entity
 */
@Entity
@Table(name = "publishes")
@Data
public class Publish {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "active")
    private boolean active;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private Image image;
    @Column(name = "title_names")
    private String titleNames;
    @Column(name = "text_html")
    private String textHtml;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PublishType type;
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private PublishesCategory category;
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "tags", joinColumns = @JoinColumn(name = "publish_id"))
    private List<String> tags;
    @Column(name = "date_create", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateCreate;
    @Column(name = "date_publication", columnDefinition = "TIMESTAMP")
    private LocalDateTime datePublication;
    @ManyToMany
    @JoinTable(
            name = "publishes_likes",
            joinColumns = { @JoinColumn(name = "publish_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id")}
    )
    private Set<User> likes = new HashSet<>();

    public static Publish newPublishByAuthorAndName(User author, String name) {
        Publish publish = new Publish();
        publish.setActive(false);
        publish.setTitleNames(name);
        publish.setAuthor(author);
        publish.setImage(null);
        publish.setTextHtml("<p>Текст публікації</p>");
        publish.setType(PublishType.CUSTOM);
        publish.setDateCreate(LocalDateTime.now());
        return publish;
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
