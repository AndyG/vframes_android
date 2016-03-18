package data.json.move;

import com.google.gson.JsonObject;

import java.util.List;

import data.model.input.InputElement;
import data.model.input.InputParser;
import data.model.move.IMoveListEntry;
import data.model.move.MoveListEntry;

public class MoveListEntryJsonAdapter {

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
