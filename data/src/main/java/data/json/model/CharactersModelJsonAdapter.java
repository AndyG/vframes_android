package data.json.model;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import data.json.character.SFCharacterJsonAdapter;
import data.model.*;
import data.model.character.SFCharacter;

public class CharactersModelJsonAdapter {

    public static CharactersModel jsonToCharactersModel(JsonObject jsonObject) {
        Map<CharacterName, SFCharacter> characterMap = new HashMap<>();
        for (CharacterName characterName : CharacterName.values()) {
            JsonObject characterJson = jsonObject.getAsJsonObject(characterName.toString());
            SFCharacter sfCharacter = SFCharacterJsonAdapter.JsonToCharacter(characterJson);
            characterMap.put(characterName, sfCharacter);
        }
        return new CharactersModel(characterMap);
    }

    public static JsonObject CharactersModelToJson(CharactersModel charactersModel) {
        JsonObject jsonObject = new JsonObject();

        for (Map.Entry<CharacterName, SFCharacter> characterEntry : charactersModel.getCharacters().entrySet()) {
            JsonObject characterJson = SFCharacterJsonAdapter.CharacterToJson(characterEntry.getValue());
            jsonObject.add(characterEntry.getKey().toString(), characterJson);
        }

        return jsonObject;
    }

}
