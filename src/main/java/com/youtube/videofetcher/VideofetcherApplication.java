package com.youtube.videofetcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling   // ✅ REQUIRED
public class VideofetcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideofetcherApplication.class, args);
    }
}
