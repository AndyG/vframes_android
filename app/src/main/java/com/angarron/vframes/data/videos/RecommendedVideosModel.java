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
}
