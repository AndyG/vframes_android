package data.model;

import java.util.Map;

import data.model.character.SFCharacter;

public class CharactersModel implements ICharactersModel {

    public Map<CharacterID, SFCharacter> characters;

    //Map of character name to character data representation.
    public CharactersModel(Map<CharacterID, SFCharacter> characters) {
        assertAllCharactersPresent(characters);
        this.characters = characters;
    }

    @Override
    public Map<CharacterID, SFCharacter> getCharacters() {
        return characters;
    }

    @Override
    public SFCharacter getCharacter(CharacterID targetCharacter) {
        return characters.get(targetCharacter);
    }

    private void assertAllCharactersPresent(Map<CharacterID, SFCharacter> testCharacterMap) {
        for (CharacterID characterID : CharacterID.values()) {
            if (!testCharacterMap.containsKey(characterID)) {
                throw new IllegalArgumentException("characters map must contain all characters. missing: " + characterID);
            }
        }
    }
}
