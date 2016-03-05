package com.angarron.vframes.data.twitch;

public class TwitchStream {

    private String channelDisplayName;
    private String status;
    private String channelUrl;
    private Integer viewerCount;
    private String previewUrl;

    public TwitchStream(String channelDisplayName, String status, String channelUrl, Integer viewerCount, String previewUrl) {
        this.channelDisplayName = channelDisplayName;
        this.status = status;
        this.channelUrl = channelUrl;
        this.viewerCount = viewerCount;
        this.previewUrl = previewUrl;
    }

    public String getChannelDisplayName() {
        return channelDisplayName;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public Integer getViewerCount() {
        return viewerCount;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public String getStatus() {
        return status;
    }
}
