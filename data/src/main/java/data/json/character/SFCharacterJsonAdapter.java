package data.json.character;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.json.move.MoveListMoveJsonAdapter;
import data.model.character.SFCharacter;
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
        return new SFCharacter(jsonToMoveList(moveListJson));
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
                movesList.add(MoveListMoveJsonAdapter.JsonToMove(moveJson.getAsJsonObject()));
            }

            moveList.put(MoveCategory.fromString(categoryKey), movesList);
        }

        return moveList;
    }

    private static JsonArray moveListToJsonArray(List<IMoveListEntry> moves) {
        JsonArray movesJson = new JsonArray();

        for (IMoveListEntry move : moves) {
            movesJson.add(MoveListMoveJsonAdapter.MoveToJson(move));
        }

        return movesJson;
    }
}
