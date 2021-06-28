package com.example.springmvc.dommain;

import com.example.springmvc.dommain.enums.Type;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "galleries")
@Data
public class Gallery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JoinColumn(name = "owner_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User owner;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;
    @Column(name = "name")
    private String name;
    @ManyToMany
    @JoinTable(
            name = "gallery_images",
            joinColumns = {@JoinColumn(name = "gallery_id")},
            inverseJoinColumns = {@JoinColumn(name = "image_id")}
    )
    private List<Image> images;


    @Override
    public String toString() {
        return "Gallery{" +
                "id=" + id +
                ", owner=" + owner +
                ", type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
