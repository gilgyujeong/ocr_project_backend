package com.example.ocr_project.repository;

import com.example.ocr_project.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UploadRepository extends JpaRepository<Image, Long> {
    @Query("SELECT i FROM Image i WHERE i.carNumber = :carNumber AND i.isCal = false")
    List<Image> findAllByCarNumber(@Param("carNumber") String carNumber);
}
