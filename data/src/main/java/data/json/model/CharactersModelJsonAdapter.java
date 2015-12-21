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
            JsonObject characterJson = jsonObject.getAsJsonObject(characterID.toString());
            SFCharacter sfCharacter = SFCharacterJsonAdapter.JsonToCharacter(characterJson);
            characterMap.put(characterID, sfCharacter);
        }
        return new CharactersModel(characterMap);
    }

    public static JsonObject CharactersModelToJson(ICharactersModel charactersModel) {
        JsonObject jsonObject = new JsonObject();

        for (Map.Entry<CharacterID, SFCharacter> characterEntry : charactersModel.getCharacters().entrySet()) {
            JsonObject characterJson = SFCharacterJsonAdapter.CharacterToJson(characterEntry.getValue());
            jsonObject.add(characterEntry.getKey().toString(), characterJson);
        }

        return jsonObject;
    }

}
