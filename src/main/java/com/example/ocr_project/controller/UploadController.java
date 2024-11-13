package com.example.ocr_project.controller;

import com.example.ocr_project.dto.request.RequestImageFindDto;
import com.example.ocr_project.dto.request.RequestImageUploadDto;
import com.example.ocr_project.dto.response.ResponseDataDto;
import com.example.ocr_project.dto.response.ResponseStatusDto;
import com.example.ocr_project.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UploadController {

    private final UploadService uploadService;

    @PostMapping("/upload")
    public ResponseStatusDto imageUpload(@RequestParam("file")MultipartFile multipartFile) {
        
        return uploadService.imageUpload(multipartFile);
    }

    @PostMapping("/find")
    public ResponseDataDto<?> imageFind(@RequestBody RequestImageFindDto requestImageFindDto) {
        return uploadService.imageFind(requestImageFindDto);
    }

    @PutMapping("/calculate/{imageId}")
    public ResponseStatusDto imageUpdate(@PathVariable("imageId") Long imageId) {
        return uploadService.imageUpdate(imageId);
    }

}
