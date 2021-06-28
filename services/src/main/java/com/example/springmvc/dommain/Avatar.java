package com.example.springmvc.dommain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@Table(name="avatars")
@ToString(includeFieldNames = true)
public class Avatar {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="image_id")
    private Image image;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="small_image_id")
    private Image smallImage;
}
