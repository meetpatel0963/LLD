package com.example.tollservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class TollServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TollServiceApplication.class, args);
    }

}

