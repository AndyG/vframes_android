package data.model.move;

/**
 * Created by andy on 12/20/15
 */
public enum MoveCategory {
    NORMALS("normals"),
    SPECIALS("specials"),
    VMOVES("vmoves");

    private String name;

    MoveCategory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static MoveCategory fromString(String category) {
        switch (category) {
            case "normals":
                return NORMALS;
            case "specials":
                return SPECIALS;
            case "vmoves":
                return VMOVES;
            default:
                throw new RuntimeException("error parsing move category: " + category);
        }
    }
}
