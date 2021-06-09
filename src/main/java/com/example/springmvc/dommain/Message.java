package com.example.springmvc.dommain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Message for thinks(inProgress)
 */
@Entity
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @NotBlank(message = "Це поле не може бути пустим")
    @Size(max = 2048, message ="Повідомлення надто довге")
    private String text;
    @Size(max = 50, message = "Тег надто довгий")
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private  User author;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private Image image;

    public Message() {
    }

    public Message(String text, String tag,User user) {
        this.text = text;
        this.tag = tag;
        this.author = user;
    }



}
