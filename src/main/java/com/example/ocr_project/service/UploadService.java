package com.example.ocr_project.service;

import com.example.ocr_project.dto.request.RequestImageFindDto;
import com.example.ocr_project.dto.response.ResponseDataDto;
import com.example.ocr_project.dto.response.ResponseParkHistoryDto;
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
import java.util.List;

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

            String imagePath = "C:/Users/AI-00/Desktop/ocr_project/ocr_project_frontend/ocr_project/public/upload/" + fileName;
            String ocrResult = getOcr(imagePath);

            Image image = Image.builder()
                    .imageName(fileName)
                    .carNumber(ocrResult.substring(ocrResult.length() - 6, ocrResult.length() - 2))
                    .isCal(false)
                    .build();
            uploadRepository.save(image);

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

    public ResponseDataDto<?> imageFind(RequestImageFindDto requestImageFindDto) {
        List<Image> imageList = uploadRepository.findAllByCarNumber(requestImageFindDto.getCarNumber());
        List<ResponseParkHistoryDto> responseParkHistoryDtoList = imageList.stream().map(ResponseParkHistoryDto::new).toList();

        return new ResponseDataDto<>(responseParkHistoryDtoList);
    }

    @Transactional
    public ResponseStatusDto imageUpdate(Long imageId) {
        Image image = uploadRepository.findById(imageId).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 이미지 아이디 입니다."));
        image.update();

        return new ResponseStatusDto("주차금액이 정산 되었습니다.", 200);
    }
}
