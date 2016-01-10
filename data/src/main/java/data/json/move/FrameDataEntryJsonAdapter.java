package data.json.move;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import data.model.move.IFrameDataEntry;
import data.model.move.MoveType;
import data.model.move.TypicalFrameDataEntry;

public class FrameDataEntryJsonAdapter {

    public static JsonObject MoveToJson(IFrameDataEntry move) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.add("name", new JsonPrimitive(move.getDisplayName()));

        MoveType type = move.getMoveType();
        jsonObject.add("type", new JsonPrimitive(type.toString()));
        jsonObject.add("data", constructMoveDataJSON(move));

        return jsonObject;
    }

    public static IFrameDataEntry JsonToMove(JsonObject moveJson) {
        MoveType moveType = MoveType.fromString(moveJson.get("type").getAsString());

        switch (moveType) {
            case TYPE_0:
                return constructHardCodedFrameDataEntry(moveJson);
            case TYPE_1:
                return constructTypicalMove(moveJson);
            default:
                throw new RuntimeException("Invalid move type");
        }
    }

    private static JsonElement constructMoveDataJSON(IFrameDataEntry move) {
        MoveType type = move.getMoveType();

        switch (type) {
            case TYPE_1:
                TypicalFrameDataEntry typicalFrameDataEntry = (TypicalFrameDataEntry) move;
                JsonObject jsonObject = new JsonObject();
                jsonObject.add("startupFrames", new JsonPrimitive(typicalFrameDataEntry.getStartupFrames()));
                jsonObject.add("activeFrames", new JsonPrimitive(typicalFrameDataEntry.getActiveFrames()));
                jsonObject.add("recoveryFrames", new JsonPrimitive(typicalFrameDataEntry.getRecoveryFrames()));

                jsonObject.add("blockstunFrames", new JsonPrimitive(typicalFrameDataEntry.getBlockstunFrames()));
                jsonObject.add("hitstunFrames", new JsonPrimitive(typicalFrameDataEntry.getHitstunFrames()));

                jsonObject.add("damage", new JsonPrimitive(typicalFrameDataEntry.getDamageValue()));
                jsonObject.add("stun", new JsonPrimitive(typicalFrameDataEntry.getStunValue()));

                return jsonObject;

            default:
                throw new RuntimeException("Invalid move type: " + type.toString());
        }
    }

    private static IFrameDataEntry constructHardCodedFrameDataEntry(JsonObject moveJson) {
        String name = moveJson.get("nameID").getAsString();
        MoveType type = MoveType.TYPE_0;

        JsonObject moveDataJson = moveJson.getAsJsonObject("data");

        int startupFrames = moveDataJson.get("startupFrames").getAsInt();
        int activeFrames = moveDataJson.get("activeFrames").getAsInt();
        int recoveryFrames = moveDataJson.get("recoveryFrames").getAsInt();

        int blockAdvantage = moveDataJson.get("blockAdvantage").getAsInt();
        int hitAdvantage = moveDataJson.get("hitAdvantage").getAsInt();

        int damageValue = moveDataJson.get("damage").getAsInt();
        int stunValue = moveDataJson.get("stun").getAsInt();

        return new HardCodedFrameDataEntry(name, type, startupFrames, activeFrames, recoveryFrames, blockAdvantage, hitAdvantage, damageValue, stunValue);
    }

    private static IFrameDataEntry constructTypicalMove(JsonObject moveJson) {
        String name = moveJson.get("nameID").getAsString();
        MoveType type = MoveType.TYPE_1;

        JsonObject moveDataJson = moveJson.getAsJsonObject("data");

        int startupFrames = moveDataJson.get("startupFrames").getAsInt();
        int activeFrames = moveDataJson.get("activeFrames").getAsInt();
        int recoveryFrames = moveDataJson.get("recoveryFrames").getAsInt();

        int blockstunFrames = moveDataJson.get("blockstunFrames").getAsInt();
        int hitstunFrames = moveDataJson.get("hitstunFrames").getAsInt();

        int damageValue = moveDataJson.get("damage").getAsInt();
        int stunValue = moveDataJson.get("stun").getAsInt();

        return new TypicalFrameDataEntry(name, type, startupFrames, activeFrames, recoveryFrames, blockstunFrames,
                hitstunFrames, damageValue, stunValue);
    }
}
