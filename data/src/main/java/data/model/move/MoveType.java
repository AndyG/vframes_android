package data.model.move;

public enum MoveType {

    TYPE_0("type_0"),
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
        for (MoveType moveType : MoveType.values()) {
            if (type.equals(moveType.toString())) {
                return moveType;
            }
        }

        throw new RuntimeException("invalid type string value: " + type);
    }
}
