package data.model;

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
