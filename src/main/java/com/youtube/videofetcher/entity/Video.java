package com.youtube.videofetcher.entity;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(name = "videos", indexes = {
        @Index(columnList = "publishedAt")
})
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String videoId;

    @Column(length = 1000)
    private String title;

    @Column(length = 5000)
    private String description;

    private String channelTitle;

    private LocalDateTime publishedAt;

    // ✅ REQUIRED by JPA
    public Video() {
    }

    public Long getId() { return id; }

    public String getVideoId() { return videoId; }
    public void setVideoId(String videoId) { this.videoId = videoId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getChannelTitle() { return channelTitle; }
    public void setChannelTitle(String channelTitle) { this.channelTitle = channelTitle; }

    public LocalDateTime getPublishedAt() { return publishedAt; }
    public void setPublishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; }
}
