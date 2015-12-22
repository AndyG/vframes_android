package data.model;

import java.util.Map;

import data.model.character.SFCharacter;

public interface ICharactersModel {
    Map<CharacterID, SFCharacter> getCharacters();

    //Convenience method to get a particular character from the map of characters.
    SFCharacter getCharacter(CharacterID targetCharacter);
}
