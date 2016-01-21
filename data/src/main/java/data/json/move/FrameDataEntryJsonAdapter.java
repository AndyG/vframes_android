package data.json.move;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import data.model.move.IFrameDataEntry;
import data.model.move.IFrameDataEntryHolder;
import data.model.move.MoveType;
import data.model.move.TypicalFrameDataEntry;

public class FrameDataEntryJsonAdapter {

    private static final int DISPLAY_CODE_MISSING_VALUE = 1001;

    public static JsonObject MoveToJson(IFrameDataEntry move) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.add("name", new JsonPrimitive(move.getDisplayName()));

        MoveType type = move.getMoveType();
        jsonObject.add("type", new JsonPrimitive(type.toString()));
        jsonObject.add("data", constructMoveDataJSON(move));

        return jsonObject;
    }

    public static IFrameDataEntryHolder JsonToMove(JsonObject moveJson) {
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

    private static IFrameDataEntryHolder constructHardCodedFrameDataEntry(JsonObject moveJson) {
        String name = moveJson.get("nameID").getAsString();
        MoveType type = MoveType.TYPE_0;
        String description = parseDescription(moveJson);

        JsonObject moveDataJson = moveJson.getAsJsonObject("data");
        HardCodedFrameDataEntry frameDataEntry = parseHardCodedData(name, type, moveDataJson, description);

        HardCodedFrameDataEntry alternateFrameDataEntry = null;
        if (moveJson.has("alternateData")) {
            JsonObject alternateMoveDataJson = moveJson.getAsJsonObject("alternateData");
            alternateFrameDataEntry = parseHardCodedData(name, type, alternateMoveDataJson, description);
        }

        return new FrameDataEntryHolder(frameDataEntry, alternateFrameDataEntry);
    }

    private static HardCodedFrameDataEntry parseHardCodedData(String name, MoveType type, JsonObject moveDataJson, String description) {
        int startupFrames = DISPLAY_CODE_MISSING_VALUE;
        int activeFrames = DISPLAY_CODE_MISSING_VALUE;
        int recoveryFrames = DISPLAY_CODE_MISSING_VALUE;
        int blockAdvantage = DISPLAY_CODE_MISSING_VALUE;
        int hitAdvantage = DISPLAY_CODE_MISSING_VALUE;
        int damageValue = DISPLAY_CODE_MISSING_VALUE;
        int stunValue = DISPLAY_CODE_MISSING_VALUE;

        if (moveDataJson.has("startupFrames")) {
            startupFrames = moveDataJson.get("startupFrames").getAsInt();
        }

        if (moveDataJson.has("activeFrames")) {
            activeFrames = moveDataJson.get("activeFrames").getAsInt();
        }

        if (moveDataJson.has("recoveryFrames")) {
            recoveryFrames = moveDataJson.get("recoveryFrames").getAsInt();
        }

        if (moveDataJson.has("blockAdvantage")) {
            blockAdvantage = moveDataJson.get("blockAdvantage").getAsInt();
        }

        if (moveDataJson.has("hitAdvantage")) {
            hitAdvantage = moveDataJson.get("hitAdvantage").getAsInt();
        }

        if (moveDataJson.has("damageValue")) {
            damageValue = moveDataJson.get("damageValue").getAsInt();
        }

        if (moveDataJson.has("stunValue")) {
            stunValue = moveDataJson.get("stunValue").getAsInt();
        }

        return new HardCodedFrameDataEntry(name, type, startupFrames, activeFrames, recoveryFrames, blockAdvantage, hitAdvantage, damageValue, stunValue, description);
    }

    private static IFrameDataEntryHolder constructTypicalMove(JsonObject moveJson) {
        throw new RuntimeException("constructTypicalMove stub");
//        String name = moveJson.get("nameID").getAsString();
//        String description = parseDescription(moveJson);
//
//        MoveType type = MoveType.TYPE_1;
//
//        JsonObject moveDataJson = moveJson.getAsJsonObject("data");
//
//        int startupFrames = moveDataJson.get("startupFrames").getAsInt();
//        int activeFrames = moveDataJson.get("activeFrames").getAsInt();
//        int recoveryFrames = moveDataJson.get("recoveryFrames").getAsInt();
//
//        int blockstunFrames = moveDataJson.get("blockstunFrames").getAsInt();
//        int hitstunFrames = moveDataJson.get("hitstunFrames").getAsInt();
//
//        int damageValue = moveDataJson.get("damage").getAsInt();
//        int stunValue = moveDataJson.get("stun").getAsInt();
//
//        return new TypicalFrameDataEntry(name, type, startupFrames, activeFrames, recoveryFrames, blockstunFrames,
//                hitstunFrames, damageValue, stunValue, description);
    }

    private static String parseDescription(JsonObject moveJson) {
        String description = null;
        if (moveJson.has("descriptionID")) {
            description = moveJson.get("descriptionID").getAsString();
        }
        return description;
    }
}
