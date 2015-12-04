package data.model.move;

/**
 * Created by andy on 11/30/15
 */
public enum MoveStrength {
    LIGHT("light"),
    MEDIUM("medium"),
    HEAVY("heavy");

    private String name;

    MoveStrength(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static MoveStrength fromString(String strength) {
        switch (strength) {
            case "light":
                return LIGHT;
            case "medium":
                return MEDIUM;
            case "heavy":
                return HEAVY;
            default:
                throw new RuntimeException("invalid strength string value: " + strength);
        }
    }
}
