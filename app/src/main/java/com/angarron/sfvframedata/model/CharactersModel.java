package com.angarron.sfvframedata.model;

import com.angarron.sfvframedata.model.character.SFCharacter;
import com.angarron.sfvframedata.network.json.character.SFCharacterJsonAdapter;
import com.google.gson.JsonObject;

import java.util.Map;

/**
 * Created by andy on 12/2/15
 */
public class CharactersModel {

    public Map<String, SFCharacter> characters;

    public CharactersModel(Map<String, SFCharacter> characters) {
        this.characters = characters;
    }

    public Map<String, SFCharacter> getCharacters() {
        return characters;
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();

        for (Map.Entry<String, SFCharacter> characterEntry : characters.entrySet()) {
            JsonObject characterJson = SFCharacterJsonAdapter.CharacterToJson(characterEntry.getValue());
            jsonObject.add(characterEntry.getKey(), characterJson);
        }

        return jsonObject;
    }

}
