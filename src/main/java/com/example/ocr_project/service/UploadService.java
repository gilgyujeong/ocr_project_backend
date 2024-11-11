package com.example.ocr_project.service;

import com.example.ocr_project.dto.response.ResponseStatusDto;
import com.example.ocr_project.entity.Image;
import com.example.ocr_project.repository.UploadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class UploadService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final UploadRepository uploadRepository;

    @Transactional
    public ResponseStatusDto imageUpload(MultipartFile multipartFile) {
        MultipartFile file = multipartFile;
        if (file.isEmpty()) {
            return new ResponseStatusDto("파일이 없습니다.", 400);
        }
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir, fileName);
            File dest = path.toFile();

            file.transferTo(dest);

            Image image = Image.builder()
                    .ImageName(fileName)
                    .build();
            uploadRepository.save(image);

            return new ResponseStatusDto("파일 업로드 성공", 200);

        } catch (IOException e) {
            return new ResponseStatusDto("파일 업로드 실패: " + e.getMessage(), 400);
        }
    }
}
