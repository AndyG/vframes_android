package data.model;

public interface IDataModel {
    IDataVersion getVersion();
    ICharactersModel getCharactersModel();

    interface IDataVersion {
        int getDataVersionMajor();
        int getDataVersionMinor();
    }
}
