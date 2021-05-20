package com.example.springmvc.dommain;

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
    private boolean active;
    @Column(name = "title_images")
    private String titleImages;
    @Column(name = "title_names")
    private String titleNames;
    @Column(name = "text_html")
    private String textHtml;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;
    @Column(name = "color_text")
    @Enumerated(EnumType.STRING)
    private Color colorText;

    public static Publish EMPTY(User author) {
        Publish publish = new Publish();
        publish.setActive(false);
        publish.setAuthor(author);
        publish.setTitleNames("Назва публікації");
        publish.setTitleImages(null);
        publish.setTextHtml("<p>Текст публікації</p>");
        return publish;
    }

    public boolean isEdit(User user) {
        return user != null && (user.equals(getAuthor()) || user.isAdmin());
    }

    public boolean isAuthor(User user) {
        return author.equals(user);
    }

}
