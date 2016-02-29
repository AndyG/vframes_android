package com.angarron.vframes.data.videos;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class YoutubeVideosModel {

    private Map<String, List<YoutubeVideo>> videos;

    public void addVideo(String category, YoutubeVideo youtubeVideo) {
        if (videos == null) {
            videos = new LinkedHashMap<>();
        }

        List<YoutubeVideo> videosList;

        //Create the category if necessary
        if (videos.get(category) == null) {
            videosList = new ArrayList<>();
            videos.put(category, videosList);
        } else {
            videosList = videos.get(category);
        }

        videosList.add(youtubeVideo);
    }

    public Map<String, List<YoutubeVideo>> getVideos() {
        return videos;
    }
}
