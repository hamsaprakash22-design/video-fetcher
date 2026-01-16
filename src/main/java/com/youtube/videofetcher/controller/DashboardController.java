package com.youtube.videofetcher.controller;

import com.youtube.videofetcher.repository.VideoRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DashboardController {

    private final VideoRepository repository;

    public DashboardController(VideoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        Pageable pageable = PageRequest.of(
                0, 20, Sort.by("publishedAt").descending()
        );
        model.addAttribute("videos", repository.findAll(pageable).getContent());
        return "videos";
    }

    @GetMapping("/search")
    public String search(@RequestParam String q, Model model) {
        Pageable pageable = PageRequest.of(0, 20);
        model.addAttribute(
                "videos",
                repository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        q, q, pageable
                ).getContent()
        );
        return "videos";
    }
}
