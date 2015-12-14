package data.model;

public class DataVersion implements IDataModel.IDataVersion {

    private int major;
    private int minor;

    public DataVersion(int major, int minor) {
        this.major = major;
        this.minor = minor;
    }

    @Override
    public int getDataVersionMajor() {
        return major;
    }

    @Override
    public int getDataVersionMinor() {
        return minor;
    }

    @Override
    public String toString() {
        return major + "." + minor;
    }
}
