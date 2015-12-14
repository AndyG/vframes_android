package data.model;

public class DataModel implements IDataModel {

    private ICharactersModel charactersModel;
    private IDataVersion dataVersion;

    public DataModel(ICharactersModel charactersModel, IDataVersion dataVersion) {
        this.charactersModel = charactersModel;
        this.dataVersion = dataVersion;
    }

    @Override
    public IDataVersion getVersion() {
        return dataVersion;
    }

    @Override
    public ICharactersModel getCharactersModel() {
        return charactersModel;
    }
}
