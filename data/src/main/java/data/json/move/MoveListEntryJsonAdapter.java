package data.json.move;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.List;

import data.model.input.InputElement;
import data.model.input.InputParser;
import data.model.move.IMoveListEntry;
import data.model.move.MoveListEntry;

public class MoveListEntryJsonAdapter {

    public static JsonObject MoveToJson(IMoveListEntry move) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.add("nameID", new JsonPrimitive(move.getNameId()));
        //jsonObject.add("input", new JsonPrimitive(move.getInputString()));
        jsonObject.add("pretextID", new JsonPrimitive(move.getPretextId()));
        jsonObject.add("posttextID", new JsonPrimitive(move.getPosttextId()));
        jsonObject.add("descriptionID", new JsonPrimitive(move.getDescriptionId()));

        return jsonObject;
    }

    public static IMoveListEntry JsonToMove(JsonObject moveJson) {
        InputParser inputParser = new InputParser();
        String nameId = getJsonStringValue(moveJson, "nameID");
        String input = getJsonStringValue(moveJson, "input");
        String pretextId = getJsonStringValue(moveJson, "pretextID");
        String posttextId = getJsonStringValue(moveJson, "posttextID");
        String descriptionId = getJsonStringValue(moveJson, "description");
        List<InputElement> inputElementList = inputParser.parseInputString(input);

        return new MoveListEntry(nameId, pretextId, posttextId, descriptionId, inputElementList);
    }

    private static String getJsonStringValue(JsonObject jsonObject, String key) {
        if (jsonObject.has(key)) {
            return jsonObject.get(key).getAsString();
        } else {
            return null;
        }
    }
}
