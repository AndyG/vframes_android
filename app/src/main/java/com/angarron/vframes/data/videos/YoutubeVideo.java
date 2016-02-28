package com.angarron.vframes.data.videos;

public class YoutubeVideo {

    private String title;
    private String author;
    private String thumbnailUrl;

    public YoutubeVideo(String title, String author, String thumbnailUrl) {
        this.title = title;
        this.author = author;
        this.thumbnailUrl = thumbnailUrl;
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
}
