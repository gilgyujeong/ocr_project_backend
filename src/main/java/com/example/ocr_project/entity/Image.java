package com.example.ocr_project.entity;

import com.example.ocr_project.util.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image extends Timestamped {

    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String imageName;

    @Column
    private String carNumber;

    @Column
    private boolean isCal;

    public void update() {
        this.isCal = true;
    }
}
