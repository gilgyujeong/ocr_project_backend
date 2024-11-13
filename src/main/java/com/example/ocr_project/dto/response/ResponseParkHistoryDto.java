package com.example.ocr_project.dto.response;

import com.example.ocr_project.entity.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ResponseParkHistoryDto {
    private Long imageId;
    private String carNumber;
    private String imageName;
    private LocalDateTime createdAt;

    public ResponseParkHistoryDto(Image image) {
        this.imageId = image.getId();
        this.carNumber = image.getCarNumber();
        this.imageName = image.getImageName();
        this.createdAt = image.getCreatedAt();
    }
}
