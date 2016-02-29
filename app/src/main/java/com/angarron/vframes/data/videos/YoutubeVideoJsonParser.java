package com.angarron.vframes.data.videos;

import com.google.gson.JsonObject;

public class YoutubeVideoJsonParser {

    public static YoutubeVideo parseYoutubeVideoJson(JsonObject jsonObject, String description, String id) {
        JsonObject snippet = jsonObject.getAsJsonObject("snippet");

        String title = snippet.get("title").getAsString();
        String thumbnailUrl = getThumbnailUrl(snippet);
        String channelTitle = snippet.get("channelTitle").getAsString();

        return new YoutubeVideo(title, channelTitle, thumbnailUrl, description, id);
    }

    private static String getThumbnailUrl(JsonObject snippet) {
        JsonObject thumbnailsJson = snippet.getAsJsonObject("thumbnails");
        JsonObject thumbnailJson = thumbnailsJson.getAsJsonObject("medium");
        return thumbnailJson.get("url").getAsString();
    }
}
