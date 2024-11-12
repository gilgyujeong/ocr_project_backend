package com.example.ocr_project.service;

import com.example.ocr_project.dto.response.ResponseStatusDto;
import com.example.ocr_project.entity.Image;
import com.example.ocr_project.repository.UploadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

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

    @Autowired
    private WebClient.Builder webClientBuilder;

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

            String imagePath = "D:/uploads/" + fileName;
            String ocrResult = getOcr(imagePath);

            return new ResponseStatusDto("파일 업로드 성공 : " + ocrResult, 200);

        } catch (IOException e) {
            return new ResponseStatusDto("파일 업로드 실패: " + e.getMessage(), 400);
        }
    }

    public String getOcr(String imagePath) {
        try {
            String result = webClientBuilder.build()
                    .post()
                    .uri("http://localhost:8000/ocr")
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .bodyValue("{\"imagePath\": \"" + imagePath + "\"}")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return result;
        } catch (WebClientResponseException e) {
            return "Error: " + e.getResponseBodyAsString();
        }

    }
}
