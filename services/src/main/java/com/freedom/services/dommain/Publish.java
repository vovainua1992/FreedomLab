package com.freedom.services.dommain;

import com.freedom.services.dommain.enums.PublishType;
import lombok.Data;

import javax.persistence.*;

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


    public static Publish newForAuthor(User author) {
        Publish publish = new Publish();
        publish.setActive(false);
        publish.setAuthor(author);
        publish.setTitleNames("Назва публікації");
        publish.setImage(null);
        publish.setTextHtml("<p>Текст публікації</p>");
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

}
