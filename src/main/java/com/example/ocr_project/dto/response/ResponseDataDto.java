package com.example.ocr_project.dto.response;

import lombok.Getter;

@Getter
public class ResponseDataDto<T> {
    private T data;

    public ResponseDataDto(T data) {
        this.data = data;
    }
}
