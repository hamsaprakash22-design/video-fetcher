package com.youtube.videofetcher.scheduler;

import com.youtube.videofetcher.service.YouTubeFetchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VideoScheduler {

    private final YouTubeFetchService service;

    @Value("${youtube.fetch.enabled}")
    private boolean enabled;

    public VideoScheduler(YouTubeFetchService service) {
        this.service = service;
    }

    @Scheduled(fixedRateString = "${youtube.fetch.interval}")
    public void fetchVideos() {
        if (enabled) {
            service.fetchAndSaveVideos();
        }
    }
}


