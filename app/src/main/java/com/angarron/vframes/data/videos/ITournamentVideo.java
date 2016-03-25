package com.angarron.vframes.data.videos;

import data.model.CharacterID;

public interface ITournamentVideo extends IVideo {
    CharacterID getFirstCharacter();
    CharacterID getSecondCharacter();
}
