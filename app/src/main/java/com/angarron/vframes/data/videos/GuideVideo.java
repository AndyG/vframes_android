package com.angarron.vframes.data.videos;

import com.google.gson.annotations.SerializedName;

import data.model.CharacterID;

public class GuideVideo implements IGuideVideo {

    @SerializedName("desc")
    String description;

    @SerializedName("character")
    private String character;

    @SerializedName("videoID")
    private String videoId;

    @Override
    public String getVideoID() {
        return videoId;
    }

    @Override
    public String getSubtext() {
        return description;
    }

    @Override
    public CharacterID getCharacter() {
        return CharacterID.getCharacterFromString(character);
    }
}
