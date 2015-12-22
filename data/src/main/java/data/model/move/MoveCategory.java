package data.model.move;

/**
 * Created by andy on 12/20/15
 */
public enum MoveCategory {
    NORMALS("normals"),
    SPECIALS("specials"),
    VMOVES("vmoves"),
    CRITICAL_ARTS("critical_arts"),
    UNIQUE_MOVES("unique_moves");

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
            case "critical_arts":
                return CRITICAL_ARTS;
            case "unique_moves":
                return UNIQUE_MOVES;
            default:
                throw new RuntimeException("error parsing move category: " + category);
        }
    }
}
