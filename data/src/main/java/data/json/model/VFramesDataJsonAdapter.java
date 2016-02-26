package data.json.model;

import com.google.gson.JsonObject;

import data.model.DataModel;
import data.model.ICharactersModel;
import data.model.IDataModel;

public class VFramesDataJsonAdapter {

    public static IDataModel jsonToDataModel(JsonObject jsonObject) {
        JsonObject charactersJson = jsonObject.getAsJsonObject("characters");
        ICharactersModel charactersModel = CharactersModelJsonAdapter.jsonToCharactersModel(charactersJson);

        Integer version = jsonObject.get("version").getAsInt();
        return new DataModel(version, charactersModel);
    }
}
