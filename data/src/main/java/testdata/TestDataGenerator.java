package testdata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;

import data.json.character.SFCharacterJsonAdapter;
import data.model.character.SFCharacter;
import testdata.testdata.CharacterFactory;

public class TestDataGenerator {

    //TODO: make this accept parameters and write to file instead of print to console
    public static void main(String[] args) {
        JsonObject dataJson = generateRandomData();

        if (args.length != 0) {
            String fileName = args[0];
            writeJsonToFile(dataJson, fileName);
        }

        System.out.println(dataJson.toString());
    }

    private static void writeJsonToFile(JsonObject dataJson, String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(dataJson);

        try (FileWriter file = new FileWriter(fileName)) {
            file.write(prettyJson);
            System.out.println("Successfully wrote Json object to file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private static JsonObject generateRandomData() {
        JsonObject charactersJsonObject = new JsonObject();

        CharacterFactory characterFactory = new CharacterFactory();
        for (int i = 0; i < 16; i++) {
            SFCharacter character = characterFactory.generateCharacter(5);
            JsonObject characterJson = SFCharacterJsonAdapter.CharacterToJson(character);
            charactersJsonObject.add(character.getName(), characterJson);
        }

        JsonObject dataJson = new JsonObject();
        dataJson.add("characters", charactersJsonObject);
        return dataJson;
    }
}
