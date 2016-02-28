package com.angarron.vframes.data.videos;

import com.google.gson.JsonObject;

public class YoutubeVideoJsonParser {

    public static YoutubeVideo parseYoutubeVideoJson(String videoId, JsonObject jsonObject) {
        JsonObject snippet = jsonObject.getAsJsonObject("snippet");

        String title = snippet.get("title").getAsString();
        String thumbnailUrl = getThumbnailUrl(snippet);
        String channelTitle = snippet.get("channelTitle").getAsString();

        return new YoutubeVideo(title, channelTitle, thumbnailUrl);
    }

    private static String getThumbnailUrl(JsonObject snippet) {
        JsonObject thumbnailsJson = snippet.getAsJsonObject("thumbnails");
        JsonObject thumbnailJson = thumbnailsJson.getAsJsonObject("medium");
        return thumbnailJson.get("url").getAsString();
    }
}
