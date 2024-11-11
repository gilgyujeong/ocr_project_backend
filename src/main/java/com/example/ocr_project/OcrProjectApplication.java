package com.example.ocr_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OcrProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(OcrProjectApplication.class, args);
	}

}
