package com.angarron.vframes.data.videos;

public class RecommendedVideo {
    private String videoId;
    private String description;

    public RecommendedVideo(String videoId, String description) {
        this.videoId = videoId;
        this.description = description;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getDescription() {
        return description;
    }
}
