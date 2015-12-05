package data.json.character;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import data.json.move.MoveJsonAdapter;
import data.model.character.SFCharacter;
import data.model.move.IDisplayableMove;

/**
 * Created by andy on 12/2/15
 */
public class SFCharacterJsonAdapter {

    public static JsonObject CharacterToJson(SFCharacter character) {
        JsonArray movesJson = new JsonArray();
        for (IDisplayableMove move : character.getMoves()) {
            movesJson.add(MoveJsonAdapter.MoveToJson(move));
        }

        JsonObject characterJson = new JsonObject();
        characterJson.add("moves", movesJson);
        return characterJson;
    }

    public static SFCharacter JsonToCharacter(JsonObject characterJson) {
        List<IDisplayableMove> movesList = new ArrayList<>();
        JsonArray movesJson = characterJson.getAsJsonArray("moves");
        for (JsonElement moveJson : movesJson) {
            movesList.add(MoveJsonAdapter.JsonToMove(moveJson.getAsJsonObject()));
        }

        return new SFCharacter(movesList);
    }
}
