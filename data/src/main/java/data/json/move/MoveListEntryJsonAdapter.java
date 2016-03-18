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

        jsonObject.add("name", new JsonPrimitive(move.getName()));
        //jsonObject.add("input", new JsonPrimitive(move.getInputString()));
        jsonObject.add("pretext", new JsonPrimitive(move.getPretext()));
        jsonObject.add("posttext", new JsonPrimitive(move.getPosttext()));
        jsonObject.add("description", new JsonPrimitive(move.getDescription()));

        return jsonObject;
    }

    public static IMoveListEntry JsonToMove(JsonObject moveJson) {
        InputParser inputParser = new InputParser();
        String name = getJsonStringValue(moveJson, "name");
        String input = getJsonStringValue(moveJson, "input");
        String pretext = getJsonStringValue(moveJson, "pretext");
        String posttext = getJsonStringValue(moveJson, "posttext");
        String description = getJsonStringValue(moveJson, "description");
        List<InputElement> inputElementList = inputParser.parseInputString(input);

        return new MoveListEntry(name, pretext, posttext, description, inputElementList);
    }

    private static String getJsonStringValue(JsonObject jsonObject, String key) {
        if (jsonObject.has(key)) {
            return jsonObject.get(key).getAsString();
        } else {
            return null;
        }
    }
}
