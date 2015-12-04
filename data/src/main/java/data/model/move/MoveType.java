package data.model.move;

/**
 * Created by andy on 11/30/15
 */
public enum MoveType {

    TYPE_1("type_1");

    private String name;

    MoveType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static MoveType fromString(String type) {
        if (type.equals("type_1")) {
            return TYPE_1;
        } else {
            throw new RuntimeException("invalid type string value: " + type);
        }
    }
}
