package com.angarron.sfvframedata.data;

import data.model.ICharactersModel;

public class DataModel implements IDataModel {

    private ICharactersModel charactersModel;

    public DataModel(ICharactersModel charactersModel) {
        this.charactersModel = charactersModel;
    }

    @Override
    public ICharactersModel getCharactersModel() {
        return charactersModel;
    }
}
