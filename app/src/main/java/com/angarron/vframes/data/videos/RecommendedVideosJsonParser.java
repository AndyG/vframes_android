package com.angarron.vframes.data.videos;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RecommendedVideosJsonParser {
    private static final String VIDEO_ID_KEY = "videoID";
    private static final String DESCRIPTION_KEY = "description";

    public static RecommendedVideosModel parseVideos(JsonArray videosJson) {
        LinkedHashMap<String, List<RecommendedVideo>> recommendedVideosModel = new LinkedHashMap<>();

        for (JsonElement categoryElement : videosJson) {

            JsonObject categoryJson = categoryElement.getAsJsonObject();

            for (Map.Entry<String, JsonElement> entry : categoryJson.getAsJsonObject().entrySet()) {

                String categoryString = entry.getKey();
                System.out.println(categoryString);

                List<RecommendedVideo> recommendedVideos = new ArrayList<>();

                for (JsonElement videoJson : categoryJson.get(categoryString).getAsJsonArray()) {
                    recommendedVideos.add(parseVideo(videoJson.getAsJsonObject()));
                }

                recommendedVideosModel.put(categoryString, recommendedVideos);
            }
        }

        return new RecommendedVideosModel(recommendedVideosModel);
    }

    private static RecommendedVideo parseVideo(JsonObject videoJson) {
        String videoId = videoJson.get(VIDEO_ID_KEY).getAsString();
        String description = videoJson.get(DESCRIPTION_KEY).getAsString();
        return new RecommendedVideo(videoId, description);
    }

}
