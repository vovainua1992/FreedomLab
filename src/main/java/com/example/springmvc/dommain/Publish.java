package com.example.springmvc.dommain;

import javax.persistence.*;

/**
 * Publish entity
 */
@Entity
@Table(name ="publishes")
public class Publish {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private boolean active;
    @Column(name ="title_images")
    private String titleImages;
    @Column(name ="title_names")
    private String titleNames;
    @Column(name ="text_html")
    private String textHtml;
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;
    @Column(name = "color_text")
    @Enumerated(EnumType.STRING)
    private Color colorText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTitleImages() {
        return titleImages;
    }

    public void setTitleImages(String titleImages) {
        this.titleImages = titleImages;
    }

    public String getTitleNames() {
        return titleNames;
    }

    public void setTitleNames(String titleNames) {
        this.titleNames = titleNames;
    }

    public String getTextHtml() {
        return textHtml;
    }

    public void setTextHtml(String textHtml) {
        this.textHtml = textHtml;
    }


    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Color getColorText() {
        return colorText;
    }

    public void setColorText(Color colorText) {
        this.colorText = colorText;
    }
}
