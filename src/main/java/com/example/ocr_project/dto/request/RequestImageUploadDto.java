package com.example.ocr_project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter
@Builder
public class RequestImageUploadDto {

    private MultipartFile image;
}
