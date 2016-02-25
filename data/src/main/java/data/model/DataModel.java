package data.model;

public class DataModel implements IDataModel {

    private Integer version;
    private ICharactersModel charactersModel;

    public DataModel(Integer version, ICharactersModel charactersModel) {
        this.version = version;
        this.charactersModel = charactersModel;
    }

    @Override
    public ICharactersModel getCharactersModel() {
        return charactersModel;
    }

    @Override
    public Integer getVersion() {
        return version;
    }
}
