package data.json.model;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import data.json.character.SFCharacterJsonAdapter;
import data.model.*;
import data.model.character.SFCharacter;

public class CharactersModelJsonAdapter {

    public static CharactersModel jsonToCharactersModel(JsonObject jsonObject) {
        Map<CharacterID, SFCharacter> characterMap = new HashMap<>();
        for (CharacterID characterID : CharacterID.values()) {
            System.out.println("Parsing character : " + characterID.toString());
            String characterKey = characterID.toString();
            if (jsonObject.has(characterKey)) {
                JsonObject characterJson = jsonObject.getAsJsonObject(characterID.toString());
                SFCharacter sfCharacter = SFCharacterJsonAdapter.JsonToCharacter(characterJson);
                characterMap.put(characterID, sfCharacter);
            } else {
                characterMap.put(characterID, new SFCharacter(null, null, null));
            }
        }
        return new CharactersModel(characterMap);
    }
}
