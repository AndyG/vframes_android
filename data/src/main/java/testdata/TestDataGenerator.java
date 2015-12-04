package testdata;

import testdata.testdata.CharacterFactory;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import data.json.character.SFCharacterJsonAdapter;
import data.model.character.SFCharacter;

public class TestDataGenerator {

    public static void main(String[] args) {
        JsonObject dataJson = generateRandomData();
        System.out.println(dataJson.toString());
    }

    private static JsonObject generateRandomData() {
        JsonArray charactersArray = new JsonArray();
        CharacterFactory characterFactory = new CharacterFactory();
        for (int i = 0; i < 16; i++) {
            SFCharacter character = characterFactory.generateCharacter(5);
            JsonObject characterJson = SFCharacterJsonAdapter.CharacterToJson(character);
            charactersArray.add(characterJson);
        }

        JsonObject dataJson = new JsonObject();
        dataJson.add("characters", charactersArray);
        return dataJson;
    }
}
