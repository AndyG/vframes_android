package data.json.model;

import com.google.gson.JsonObject;

import data.model.DataModel;
import data.model.ICharactersModel;
import data.model.IDataModel;

public class VFramesDataJsonAdapter {

    public static IDataModel jsonToDataModel(JsonObject jsonObject) {
        JsonObject charactersJson = jsonObject.getAsJsonObject("characters");
        ICharactersModel charactersModel = CharactersModelJsonAdapter.jsonToCharactersModel(charactersJson);

        return new DataModel(charactersModel);
    }

    public static JsonObject dataModelToJson(IDataModel dataModel) {

        JsonObject charactersJson = CharactersModelJsonAdapter.CharactersModelToJson(dataModel.getCharactersModel());

        JsonObject dataJson = new JsonObject();
        dataJson.add("characters", charactersJson);

        return dataJson;
    }
}
