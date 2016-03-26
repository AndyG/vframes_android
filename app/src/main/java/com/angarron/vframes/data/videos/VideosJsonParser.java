package com.angarron.vframes.data.videos;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VideosJsonParser {

    public static List<IGuideVideo> parseGuideVideos(JsonArray videosJson) {
        Gson gson = new Gson();
        GuideVideo[] guideVideos = gson.fromJson(videosJson, GuideVideo[].class);

        List<IGuideVideo> guideVideosList = new ArrayList<>();
        Collections.addAll(guideVideosList, guideVideos);
        return guideVideosList;
    }

    public static List<ITournamentVideo> parseTournamentVideos(JsonArray videosJson) {
        Gson gson = new Gson();
        TournamentVideo[] tournamentVideos = gson.fromJson(videosJson, TournamentVideo[].class);

        List<ITournamentVideo> tournamentVideosList = new ArrayList<>();
        Collections.addAll(tournamentVideosList, tournamentVideos);
        return tournamentVideosList;
    }

}
