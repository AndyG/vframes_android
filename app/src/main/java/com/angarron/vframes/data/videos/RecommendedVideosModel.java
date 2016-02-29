package com.angarron.vframes.data.videos;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RecommendedVideosModel {

    private Map<String, List<RecommendedVideo>> videos;

    public RecommendedVideosModel(LinkedHashMap<String, List<RecommendedVideo>> videos) {
        this.videos = videos;
    }

    public Map<String, List<RecommendedVideo>> getVideos() {
        return videos;
    }

    public boolean isEmpty() {
        return videos.isEmpty();
    }

    public String getCategoryForVideo(String videoId) {
        for (String category : videos.keySet()) {
            for (RecommendedVideo video : videos.get(category)) {
                if (video.getVideoId().equals(videoId)) {
                    return category;
                }
            }
        }
        throw new RuntimeException("could not find category for video: " + videoId);
    }

    public String getDescriptionForVideo(String videoId) {
        for (String category : videos.keySet()) {
            for (RecommendedVideo video : videos.get(category)) {
                if (video.getVideoId().equals(videoId)) {
                    return video.getDescription();
                }
            }
        }
        throw new RuntimeException("could not find description for video: " + videoId);
    }
}
