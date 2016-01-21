package data.json.character;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.json.move.FrameDataEntryJsonAdapter;
import data.json.move.MoveListEntryJsonAdapter;
import data.model.character.FrameData;
import data.model.character.SFCharacter;
import data.model.move.IFrameDataEntryHolder;
import data.model.move.IMoveListEntry;
import data.model.move.MoveCategory;

public class SFCharacterJsonAdapter {

    public static JsonObject CharacterToJson(SFCharacter character) {
        JsonObject movesObject = new JsonObject();

        for (MoveCategory categoryKey : character.getMoveList().keySet()) {
            List<IMoveListEntry> category = character.getMoveList().get(categoryKey);
            JsonArray categoryArray = moveListToJsonArray(category);
            movesObject.add(categoryKey.toString(), categoryArray);
        }

        JsonObject characterJson = new JsonObject();
        characterJson.add("move_list", movesObject);
        return characterJson;
    }

    public static SFCharacter JsonToCharacter(JsonObject characterJson) {
        JsonObject moveListJson = characterJson.getAsJsonObject("move_list");
        if (characterJson.has("frame_data")) {
            JsonArray frameDataJson = characterJson.getAsJsonArray("frame_data");
            return new SFCharacter(jsonToMoveList(moveListJson), jsonToFrameData(frameDataJson));
        } else {
            return new SFCharacter(jsonToMoveList(moveListJson), null);
        }
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

    private static List<FrameData> jsonToFrameData(JsonArray frameDataArrayJson) {
        List<FrameData> frameData = new ArrayList<>();

        for (JsonElement frameDataSet : frameDataArrayJson) {
            frameData.add(parseFrameDataSet(frameDataSet.getAsJsonObject()));
        }

        return frameData;
    }

    private static FrameData parseFrameDataSet(JsonObject frameDataJsonObject) {

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

    private static JsonArray moveListToJsonArray(List<IMoveListEntry> moves) {
        JsonArray movesJson = new JsonArray();

        for (IMoveListEntry move : moves) {
            movesJson.add(MoveListEntryJsonAdapter.MoveToJson(move));
        }

        return movesJson;
    }
}
