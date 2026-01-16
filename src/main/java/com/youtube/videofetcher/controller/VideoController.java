package com.youtube.videofetcher.controller;

import com.youtube.videofetcher.entity.Video;
import com.youtube.videofetcher.repository.VideoRepository;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/videos")
public class VideoController {

    private final VideoRepository repository;

    public VideoController(VideoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Page<Video> getVideos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "publishedAt")
        );

        return repository.findAll(pageable);
    }

    @GetMapping("/search")
    public Page<Video> search(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return repository
                .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        q, q, pageable
                );
    }
}
