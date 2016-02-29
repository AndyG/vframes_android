package com.angarron.vframes.data.twitch;

import com.angarron.vframes.BuildConfig;
import com.crashlytics.android.Crashlytics;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class TwitchJsonParser {

    private static final String STREAMS_KEY = "streams";

    public List<TwitchStream> parse(JsonObject body) {
        JsonArray streamsArray = body.getAsJsonArray(STREAMS_KEY);

        List<TwitchStream> twitchStreams = new ArrayList<>();
        for (JsonElement streamJson : streamsArray) {
            try {
                twitchStreams.add(parseStream(streamJson.getAsJsonObject()));
            } catch (Exception e) {
                if (!BuildConfig.DEBUG) {
                    Crashlytics.logException(new Throwable("failed to parse twitch json"));
                }
            }
        }

        return twitchStreams;
    }

    private TwitchStream parseStream(JsonObject streamJson) throws Exception {

        JsonObject channelJson = streamJson.getAsJsonObject("channel");

        Integer viewerCount = streamJson.get("viewers").getAsInt();
        String channelDisplayName = channelJson.get("display_name").getAsString();
        String channelUrl = channelJson.get("url").getAsString();

        String previewUrl = null;
        try {
            previewUrl = streamJson.get("preview").getAsJsonObject().get("medium").getAsString();
        } catch (Exception e) {
            //no-op, it's okay if the channel doesn't have a logo
        }

        String status = null;
        try {
            status = channelJson.get("status").getAsString();
        } catch (Exception e) {
            //no-op, it's okay if the channel doesn't have a status
        }

        return new TwitchStream(channelDisplayName, status, channelUrl, viewerCount, previewUrl);
    }
}
