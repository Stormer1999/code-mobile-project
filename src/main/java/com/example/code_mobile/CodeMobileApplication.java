package com.example.code_mobile;

import com.example.code_mobile.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CodeMobileApplication {

  public static void main(String[] args) {
    SpringApplication.run(CodeMobileApplication.class, args);
  }

  @Bean
  CommandLineRunner init(StorageService storageService) {
    return args -> {
      storageService.init();
    };
  }
}
