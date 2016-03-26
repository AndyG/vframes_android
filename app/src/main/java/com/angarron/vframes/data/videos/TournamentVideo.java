package com.angarron.vframes.data.videos;

import com.google.gson.annotations.SerializedName;

import data.model.CharacterID;

public class TournamentVideo implements ITournamentVideo {

    @SerializedName("tournamentName")
    String tournamentName;

    @SerializedName("firstCharacter")
    private String firstCharacter;

    @SerializedName("secondCharacter")
    private String secondCharacter;

    @SerializedName("videoID")
    private String videoId;

    @Override
    public String getVideoID() {
        return videoId;
    }

    @Override
    public String getSubtext() {
        return tournamentName;
    }

    @Override
    public CharacterID getFirstCharacter() {
        return CharacterID.getCharacterFromString(firstCharacter);
    }

    @Override
    public CharacterID getSecondCharacter() {
        return CharacterID.getCharacterFromString(secondCharacter);
    }
}
