package com.angarron.vframes.data.videos;

public class YoutubeVideo {

    private String title;
    private String author;
    private String thumbnailUrl;
    private String description;
    private String id;

    public YoutubeVideo(String title, String author, String thumbnailUrl, String description, String id) {
        this.title = title;
        this.author = author;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }
}
