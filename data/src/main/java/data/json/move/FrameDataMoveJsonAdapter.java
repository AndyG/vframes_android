package data.json.move;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import data.model.move.IDisplayableMove;
import data.model.move.MoveStrength;
import data.model.move.MoveType;
import data.model.move.TypicalMove;

/**
 * Created by andy on 12/2/15
 */
public class FrameDataMoveJsonAdapter {

    public static JsonObject MoveToJson(IDisplayableMove move) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.add("name", new JsonPrimitive(move.getName()));
        jsonObject.add("label", new JsonPrimitive(move.getLabel()));

        MoveType type = move.getMoveType();
        jsonObject.add("type", new JsonPrimitive(type.toString()));
        jsonObject.add("data", constructMoveDataJSON(move));

        return jsonObject;
    }

    public static IDisplayableMove JsonToMove(JsonObject moveJson) {
        MoveType moveType = MoveType.fromString(moveJson.get("type").getAsString());

        switch (moveType) {
            case TYPE_1:
                return constructTypicalMove(moveJson);
            default:
                throw new RuntimeException("Invalid move type");
        }
    }

    private static JsonElement constructMoveDataJSON(IDisplayableMove move) {
        MoveType type = move.getMoveType();

        switch (type) {
            case TYPE_1:
                TypicalMove typicalMove = (TypicalMove) move;
                JsonObject jsonObject = new JsonObject();
                jsonObject.add("startupFrames", new JsonPrimitive(typicalMove.getStartupFrames()));
                jsonObject.add("activeFrames", new JsonPrimitive(typicalMove.getActiveFrames()));
                jsonObject.add("recoveryFrames", new JsonPrimitive(typicalMove.getRecoveryFrames()));

                jsonObject.add("blockstunFrames", new JsonPrimitive(typicalMove.getBlockstunFrames()));
                jsonObject.add("hitstunFrames", new JsonPrimitive(typicalMove.getHitstunFrames()));

                jsonObject.add("damage", new JsonPrimitive(typicalMove.getDamageValue()));
                jsonObject.add("stun", new JsonPrimitive(typicalMove.getStunValue()));
                jsonObject.add("strength", new JsonPrimitive(typicalMove.getStrength().toString()));

                return jsonObject;

            default:
                throw new RuntimeException("Invalid move type: " + type.toString());
        }
    }

    private static IDisplayableMove constructTypicalMove(JsonObject moveJson) {
        String name = moveJson.get("name").getAsString();
        String label = moveJson.get("label").getAsString();
        MoveType type = MoveType.TYPE_1;

        JsonObject moveDataJson = moveJson.getAsJsonObject("data");

        int startupFrames = moveDataJson.get("startupFrames").getAsInt();
        int activeFrames = moveDataJson.get("activeFrames").getAsInt();
        int recoveryFrames = moveDataJson.get("recoveryFrames").getAsInt();

        int blockstunFrames = moveDataJson.get("blockstunFrames").getAsInt();
        int hitstunFrames = moveDataJson.get("hitstunFrames").getAsInt();

        int damageValue = moveDataJson.get("damage").getAsInt();
        int stunValue = moveDataJson.get("stun").getAsInt();

        MoveStrength strength = MoveStrength.fromString(moveDataJson.get("strength").getAsString());

        return new TypicalMove(name, label, type, startupFrames, activeFrames, recoveryFrames, blockstunFrames,
                hitstunFrames, damageValue, stunValue, strength);
    }
}
