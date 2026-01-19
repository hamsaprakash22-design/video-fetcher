package com.youtube.videofetcher.service;

import com.youtube.videofetcher.entity.Video;
import com.youtube.videofetcher.repository.VideoRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;

@Service
public class YouTubeFetchService {

    private final VideoRepository videoRepository;

    @Value("${youtube.api.key}")
    private String apiKey;

    @Value("${youtube.search.queries}")
    private String queries;

    @Value("${youtube.fetch.max-results}")
    private int maxResults;

    public YouTubeFetchService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public void fetchAndSaveVideos() {

        String[] topics = queries.split(",");

        for (String topic : topics) {
            fetchForSingleTopic(topic.trim());
        }
    }

    private void fetchForSingleTopic(String query) {

        String url =
                "https://www.googleapis.com/youtube/v3/search"
                        + "?part=snippet"
                        + "&type=video"
                        + "&order=date"
                        + "&q=" + query
                        + "&maxResults=" + maxResults
                        + "&key=" + apiKey;

        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            JSONArray items = new JSONObject(response).getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {

                JSONObject item = items.getJSONObject(i);
                String videoId = item.getJSONObject("id").getString("videoId");

                if (videoRepository.existsByVideoId(videoId)) {
                    continue;
                }

                JSONObject snippet = item.getJSONObject("snippet");

                Video video = new Video();
                video.setVideoId(videoId);
                video.setTitle(snippet.getString("title"));
                video.setDescription(snippet.getString("description"));
                video.setChannelTitle(snippet.getString("channelTitle"));
                video.setPublishedAt(
                        OffsetDateTime.parse(snippet.getString("publishedAt"))
                                .toLocalDateTime()
                );

                videoRepository.save(video);
            }

        } catch (Exception e) {
            System.out.println("Failed for topic: " + query + " → " + e.getMessage());
        }
    }
}
