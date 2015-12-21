package data.json.character;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.json.move.MoveJsonAdapter;
import data.model.character.SFCharacter;
import data.model.move.IDisplayableMove;
import data.model.move.MoveCategory;

/**
 * Created by andy on 12/2/15
 */
public class SFCharacterJsonAdapter {

    public static JsonObject CharacterToJson(SFCharacter character) {
        JsonObject movesObject = new JsonObject();

        for (MoveCategory categoryKey : character.getMoves().keySet()) {
            List<IDisplayableMove> category = character.getMoves().get(categoryKey);
            JsonArray categoryArray = moveListToJsonArray(category);
            movesObject.add(categoryKey.toString(), categoryArray);
        }

        JsonObject characterJson = new JsonObject();
        characterJson.add("moves", movesObject);
        return characterJson;
    }

    public static SFCharacter JsonToCharacter(JsonObject characterJson) {
        Map<MoveCategory, List<IDisplayableMove>> moves = new HashMap<>();
        JsonObject movesJson = characterJson.getAsJsonObject("moves");

        for (Map.Entry<String, JsonElement> category : movesJson.entrySet()) {
            String categoryKey = category.getKey();
            JsonElement categoryBody = category.getValue();

            List<IDisplayableMove> movesList = new ArrayList<>();
            JsonArray categoryJson = categoryBody.getAsJsonArray();
            for (JsonElement moveJson : categoryJson) {
                movesList.add(MoveJsonAdapter.JsonToMove(moveJson.getAsJsonObject()));
            }

            moves.put(MoveCategory.fromString(categoryKey), movesList);
        }

        return new SFCharacter(moves);
    }

    private static JsonArray moveListToJsonArray(List<IDisplayableMove> moves) {
        JsonArray movesJson = new JsonArray();

        for (IDisplayableMove move : moves) {
            movesJson.add(MoveJsonAdapter.MoveToJson(move));
        }

        return movesJson;
    }
}
