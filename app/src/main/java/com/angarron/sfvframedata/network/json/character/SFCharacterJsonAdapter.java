package com.angarron.sfvframedata.network.json.character;

import com.angarron.sfvframedata.model.character.SFCharacter;
import com.angarron.sfvframedata.model.move.IDisplayableMove;
import com.angarron.sfvframedata.network.json.move.MoveJsonAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andy on 12/2/15
 */
public class SFCharacterJsonAdapter {

    public static JsonObject CharacterToJson(SFCharacter character) {
        JsonObject characterJson = new JsonObject();

        characterJson.add("name", new JsonPrimitive(character.getName()));

        JsonArray movesJson = new JsonArray();

        for (IDisplayableMove move : character.getMoves()) {
            movesJson.add(MoveJsonAdapter.MoveToJson(move));
        }

        characterJson.add("moves", movesJson);

        return characterJson;
    }

    public static SFCharacter JsonToCharacter(JsonObject characterJson) {
        String characterName = characterJson.get("name").getAsString();

        List<IDisplayableMove> movesList = new ArrayList<>();
        JsonArray movesJson = characterJson.getAsJsonArray("moves");
        for (JsonElement moveJson : movesJson) {
            movesList.add(MoveJsonAdapter.JsonToMove(moveJson.getAsJsonObject()));
        }

        return new SFCharacter(characterName, movesList);
    }
}
