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
import data.model.move.IMoveListMove;
import data.model.move.MoveCategory;

/**
 * Created by andy on 12/2/15
 */
public class SFCharacterJsonAdapter {

    public static JsonObject CharacterToJson(SFCharacter character) {
        JsonObject movesObject = new JsonObject();

        for (MoveCategory categoryKey : character.getMoveList().keySet()) {
            List<IMoveListMove> category = character.getMoveList().get(categoryKey);
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

    private static Map<MoveCategory, List<IMoveListMove>> jsonToMoveList(JsonObject moveListJson) {
        Map<MoveCategory, List<IMoveListMove>> moveList = new HashMap<>();

        for (Map.Entry<String, JsonElement> category : moveListJson.entrySet()) {
            String categoryKey = category.getKey();
            System.out.println("Parsing category: " + categoryKey);
            JsonElement categoryBody = category.getValue();
            System.out.println(categoryBody.toString());

            List<IMoveListMove> movesList = new ArrayList<>();
            JsonArray categoryJson = categoryBody.getAsJsonArray();
            for (JsonElement moveJson : categoryJson) {
                movesList.add(MoveListMoveJsonAdapter.JsonToMove(moveJson.getAsJsonObject()));
            }

            moveList.put(MoveCategory.fromString(categoryKey), movesList);
        }

        return moveList;
    }

    private static JsonArray moveListToJsonArray(List<IMoveListMove> moves) {
        JsonArray movesJson = new JsonArray();

        for (IMoveListMove move : moves) {
            movesJson.add(MoveListMoveJsonAdapter.MoveToJson(move));
        }

        return movesJson;
    }
}
