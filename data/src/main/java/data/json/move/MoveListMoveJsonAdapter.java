package data.json.move;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import data.model.move.IMoveListMove;
import data.model.move.MoveListMove;

public class MoveListMoveJsonAdapter {

    public static JsonObject MoveToJson(IMoveListMove move) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.add("nameID", new JsonPrimitive(move.getNameId()));
        jsonObject.add("input", new JsonPrimitive(move.getInputString()));
        jsonObject.add("pretextID", new JsonPrimitive(move.getPretextId()));
        jsonObject.add("posttextID", new JsonPrimitive(move.getPosttextId()));
        jsonObject.add("descriptionID", new JsonPrimitive(move.getDescriptionId()));

        return jsonObject;
    }

    public static IMoveListMove JsonToMove(JsonObject moveJson) {
        String nameId = getJsonStringValue(moveJson, "nameID");
        String input = getJsonStringValue(moveJson, "input");
        String pretextId = getJsonStringValue(moveJson, "pretextID");
        String posttextId = getJsonStringValue(moveJson, "posttextID");
        String descriptionId = getJsonStringValue(moveJson, "descriptionID");

        return new MoveListMove(nameId, input, pretextId, posttextId, descriptionId);
    }

    private static String getJsonStringValue(JsonObject jsonObject, String key) {
        if (jsonObject.has(key)) {
            return jsonObject.get(key).getAsString();
        } else {
            return null;
        }
    }
}
