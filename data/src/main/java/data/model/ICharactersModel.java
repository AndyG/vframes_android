package data.model;

import java.util.Map;

import data.model.character.SFCharacter;

public interface ICharactersModel {
    Map<CharacterName, SFCharacter> getCharacters();
}
