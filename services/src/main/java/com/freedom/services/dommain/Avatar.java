package com.freedom.services.dommain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "avatars")
@Data
@NoArgsConstructor
public class Avatar implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "origin_id", referencedColumnName = "id")
    private Image origin;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "small_id", referencedColumnName = "id")
    private Image small;
    @Column(name = "small_size")
    private int smallSize;
    @Column(name = "small_pos_x")
    private int smallPosX;
    @Column(name = "small_pos_y")
    private int smallPosY;

}
