package data.json.model;

import com.google.gson.JsonObject;

import data.model.DataModel;
import data.model.ICharactersModel;
import data.model.IDataModel;

public class VFramesDataJsonAdapter {

    public static IDataModel jsonToDataModel(JsonObject jsonObject) {
        JsonObject charactersJson = jsonObject.getAsJsonObject("characters");
        ICharactersModel charactersModel = CharactersModelJsonAdapter.jsonToCharactersModel(charactersJson);

        JsonObject versioning = jsonObject.getAsJsonObject("versioning");
        Integer version = versioning.get("dataVersion").getAsInt();
        return new DataModel(version, charactersModel);
    }
}
