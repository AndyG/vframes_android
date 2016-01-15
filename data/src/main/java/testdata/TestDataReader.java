package testdata;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import data.json.model.VFramesDataJsonAdapter;
import data.model.IDataModel;

public class TestDataReader {

    private static final String[] characterNames = new String[]{
            "ryu",
            "chun",
            "dictator",
            "birdie",
            "nash",
            "cammy",
            "claw",
            "laura",
            "ken",
            "necalli",
            "rashid",
            "mika",
            "zangief",
            "fang",
            "dhalsim",
            "karin"
    };

    //TODO: use a real parameter parser and consolidate this with TestDataGenerator
    public static void main(String[] args) {

        if (args.length != 1) {
            throw new IllegalArgumentException("No file specified in args.");
        }

        String dirPath = args[0];

        JsonObject charactersJson = new JsonObject();

        for (String characterName : characterNames) {
            String fileName = dirPath.concat("/" + characterName + "_data.json");
            System.out.println("reading file: " + fileName);
            File characterFile = new File(fileName);

            String fileData = readFileToString(characterFile);
            JsonParser parser = new JsonParser();
            JsonObject jsonData = parser.parse(fileData).getAsJsonObject();
            charactersJson.add(characterName, jsonData);
        }

        JsonObject dataModelJson = new JsonObject();
        dataModelJson.add("characters", charactersJson);

        IDataModel dataModel = VFramesDataJsonAdapter.jsonToDataModel(dataModelJson);
        System.out.println("loaded " + dataModel.getCharactersModel().getCharacters().size() + " characters.");
    }

    private static String readFileToString(File file)
    {
        byte[] fileBytes;

        try {
            fileBytes = Files.readAllBytes(Paths.get(file.getPath()));
        } catch (IOException e) {
            throw new RuntimeException("failed to read file: " + file.getPath());
        }

        return new String(fileBytes);
    }
}
