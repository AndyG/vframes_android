package data.json.character;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import data.json.move.FrameDataEntryJsonAdapter;
import data.json.move.MoveListEntryJsonAdapter;
import data.model.character.FrameData;
import data.model.character.SFCharacter;
import data.model.character.bnb.BreadAndButterCombo;
import data.model.character.bnb.BreadAndButterModel;
import data.model.move.IFrameDataEntryHolder;
import data.model.move.IMoveListEntry;
import data.model.move.MoveCategory;

public class SFCharacterJsonAdapter {

    public static SFCharacter JsonToCharacter(JsonObject characterJson) {
        Map<MoveCategory, List<IMoveListEntry>> moveList = null;
        if (characterJson.has("move_list")) {
            JsonObject moveListJson = characterJson.getAsJsonObject("move_list");
            moveList = jsonToMoveList(moveListJson);
        }

        FrameData frameData = null;
        if (characterJson.has("frame_data")) {
            JsonObject frameDataJson = characterJson.getAsJsonObject("frame_data");
            frameData = jsonToFrameData(frameDataJson);
        }

        BreadAndButterModel breadAndButterModel = null;
        if (characterJson.has("bnb_combos")) {
            JsonArray bnbsJson = characterJson.getAsJsonArray("bnb_combos");
            breadAndButterModel = jsonToBreadAndButterModel(bnbsJson);
        }

        return new SFCharacter(moveList, frameData, breadAndButterModel);
    }

    private static Map<MoveCategory, List<IMoveListEntry>> jsonToMoveList(JsonObject moveListJson) {
        Map<MoveCategory, List<IMoveListEntry>> moveList = new HashMap<>();

        for (Map.Entry<String, JsonElement> category : moveListJson.entrySet()) {
            String categoryKey = category.getKey();
            System.out.println("Parsing category: " + categoryKey);
            JsonElement categoryBody = category.getValue();
            System.out.println(categoryBody.toString());

            List<IMoveListEntry> movesList = new ArrayList<>();
            JsonArray categoryJson = categoryBody.getAsJsonArray();
            for (JsonElement moveJson : categoryJson) {
                movesList.add(MoveListEntryJsonAdapter.JsonToMove(moveJson.getAsJsonObject()));
            }

            moveList.put(MoveCategory.fromString(categoryKey), movesList);
        }

        return moveList;
    }

    private static FrameData jsonToFrameData(JsonObject frameDataJsonObject) {
        Map<MoveCategory, List<IFrameDataEntryHolder>> frameDataSet = new HashMap<>();

        for (Map.Entry<String, JsonElement> category : frameDataJsonObject.entrySet()) {
            String categoryKey = category.getKey();
            System.out.println("Parsing category: " + categoryKey);
            JsonElement categoryBody = category.getValue();
            System.out.println(categoryBody.toString());

            List<IFrameDataEntryHolder> frameDataEntries = new ArrayList<>();
            JsonArray categoryJson = categoryBody.getAsJsonArray();

            for (JsonElement moveJson : categoryJson) {
                frameDataEntries.add(FrameDataEntryJsonAdapter.JsonToMove(moveJson.getAsJsonObject()));
            }

            frameDataSet.put(MoveCategory.fromString(categoryKey), frameDataEntries);
        }

        return new FrameData(frameDataSet);
    }


    private static BreadAndButterModel jsonToBreadAndButterModel(JsonArray bnbsJson) {
        LinkedHashMap<String, List<BreadAndButterCombo>> breadAndButterModel = new LinkedHashMap<>();

        for (JsonElement categoryElement : bnbsJson) {

            JsonObject categoryJson = categoryElement.getAsJsonObject();

            for (Map.Entry<String, JsonElement> entry : categoryJson.getAsJsonObject().entrySet()) {

                String categoryString = entry.getKey();
                System.out.println(categoryString);


                List<BreadAndButterCombo> breadAndButterCombos = new ArrayList<>();

                for (JsonElement comboJson : categoryJson.get(categoryString).getAsJsonArray()) {
                    breadAndButterCombos.add(parseBreadAndButterCombo(comboJson.getAsJsonObject()));
                }

                breadAndButterModel.put(categoryString, breadAndButterCombos);
            }
        }

        return new BreadAndButterModel(breadAndButterModel);
    }

    private static BreadAndButterCombo parseBreadAndButterCombo(JsonObject comboJson) {
        String label = comboJson.get("label").getAsString();
        String inputs = comboJson.get("inputs").getAsString();
        String description = comboJson.get("description").getAsString();

        return new BreadAndButterCombo(label, inputs, description);
    }

}
