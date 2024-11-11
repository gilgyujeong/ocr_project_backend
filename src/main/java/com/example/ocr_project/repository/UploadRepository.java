package com.example.ocr_project.repository;

import com.example.ocr_project.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadRepository extends JpaRepository<Image, Long> {
}
