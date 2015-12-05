package data.model;

import java.util.Map;

import data.model.character.SFCharacter;

/**
 * Created by andy on 12/2/15
 */
public class CharactersModel {

    public Map<CharacterName, SFCharacter> characters;

    //Map of character name to character data representation.
    public CharactersModel(Map<CharacterName, SFCharacter> characters) {
        assertAllCharactersPresent(characters);
        this.characters = characters;
    }

    public Map<CharacterName, SFCharacter> getCharacters() {
        return characters;
    }

    private void assertAllCharactersPresent(Map<CharacterName, SFCharacter> testCharacterMap) {
        for (CharacterName characterName : CharacterName.values()) {
            if (!testCharacterMap.containsKey(characterName)) {
                throw new IllegalArgumentException("characters map must contain all characters. missing: " + characterName);
            }
        }
    }
}
