package testdata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import data.json.model.VFramesDataJsonAdapter;
import data.model.CharacterID;
import data.model.CharactersModel;
import data.model.DataModel;
import data.model.ICharactersModel;
import data.model.IDataModel;
import data.model.character.SFCharacter;
import testdata.testdata.CharacterFactory;

public class TestDataGenerator {

    //TODO: use a real parameter parser and consolidate this with TestDataReader
    public static void main(String[] args) {
        ICharactersModel charactersModel = generateRandomCharacters();
        IDataModel dataModel = new DataModel(charactersModel);
        JsonObject modelJson = VFramesDataJsonAdapter.dataModelToJson(dataModel);

        if (args.length != 0) {
            String fileName = args[0];
            writeJsonToFile(modelJson, fileName);
        } else {
            System.out.println("No file specified in args.");
        }

        System.out.println(modelJson.toString());
    }

    private static ICharactersModel generateRandomCharacters() {
        Map<CharacterID, SFCharacter> charactersMap = new HashMap<>();

        CharacterFactory characterFactory = new CharacterFactory();
        for (CharacterID name : CharacterID.values()) {
            SFCharacter character = characterFactory.generateCharacter();
            charactersMap.put(name, character);
        }

        return new CharactersModel(charactersMap);
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

}
