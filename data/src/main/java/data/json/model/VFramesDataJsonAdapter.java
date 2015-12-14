package data.json.model;

import com.google.gson.JsonObject;

import data.model.DataModel;
import data.model.DataVersion;
import data.model.ICharactersModel;
import data.model.IDataModel;

public class VFramesDataJsonAdapter {

    public static IDataModel jsonToDataModel(JsonObject jsonObject) {
        IDataModel.IDataVersion version = getVersion(jsonObject);

        JsonObject charactersJson = jsonObject.getAsJsonObject("characters");
        ICharactersModel charactersModel = CharactersModelJsonAdapter.jsonToCharactersModel(charactersJson);

        return new DataModel(charactersModel, version);
    }

    public static JsonObject dataModelToJson(IDataModel dataModel) {

        String version = versionToString(dataModel.getVersion());
        JsonObject charactersJson = CharactersModelJsonAdapter.CharactersModelToJson(dataModel.getCharactersModel());

        JsonObject dataJson = new JsonObject();
        dataJson.addProperty("version", version);
        dataJson.add("characters", charactersJson);

        return dataJson;
    }

    private static String versionToString(IDataModel.IDataVersion version) {
        return version.getDataVersionMajor() + "." + version.getDataVersionMinor();
    }

    private static IDataModel.IDataVersion getVersion(JsonObject jsonObject) {
        String versionStr = jsonObject.get("version").getAsString();
        int versionMajor = getVersionMajor(versionStr);
        int versionMinor = getVersionMinor(versionStr);
        return new DataVersion(versionMajor, versionMinor);
    }

    private static int getVersionMajor(String version) {
        return Integer.valueOf(version.split("\\.")[0]);
    }

    private static int getVersionMinor(String version) {
        return Integer.valueOf(version.split("\\.")[1]);
    }
}
