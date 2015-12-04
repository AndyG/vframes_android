package data.model;

import com.google.gson.JsonObject;

import data.json.character.SFCharacterJsonAdapter;
import data.model.character.SFCharacter;

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
