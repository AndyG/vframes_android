package data.model.move;

/**
 * Created by andy on 12/20/15
 */
public enum MoveCategory {
    NORMALS("normals"),
    SPECIALS("specials"),
    VSKILL("vskill"),
    VTRIGGER("vtrigger"),
    VREVERSAL("vreversal"),
    CRITICAL_ARTS("critical_arts"),
    UNIQUE_MOVES("unique_attacks"),
    THROWS("throws");

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
            case "vtrigger":
                return VTRIGGER;
            case "vreversal":
                return VREVERSAL;
            case "vskill":
                return VSKILL;
            case "critical_arts":
                return CRITICAL_ARTS;
            case "unique_attacks":
                return UNIQUE_MOVES;
            case "throws":
                return THROWS;
            default:
                throw new RuntimeException("error parsing move category: " + category);
        }
    }
}
