package com.example.ocr_project.controller;

import com.example.ocr_project.dto.request.RequestImageUploadDto;
import com.example.ocr_project.dto.response.ResponseStatusDto;
import com.example.ocr_project.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping("/upload")
    public ResponseStatusDto imageUpload(@RequestParam("image")MultipartFile multipartFile) {
        return uploadService.imageUpload(multipartFile);
    }
}
