package com.example.ocr_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ResponseStatusDto {
    private String message;
    private Integer statusCode;
}
