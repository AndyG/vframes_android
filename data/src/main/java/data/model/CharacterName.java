package data.model;

public enum CharacterName {
    RYU("ryu"),
    CHUN("chun"),
    DICTATOR("dictator"),
    BIRDIE("birdie"),
    NASH("nash"),
    CAMMY("cammy");

    private String name;

    CharacterName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static CharacterName fromString(String characterNameStr) {
        for (CharacterName characterName : values()) {
            if (characterName.name.equals(characterNameStr)) {
                return characterName;
            }
        }
        throw new IllegalArgumentException("failed to find character matching: " + characterNameStr);
    }
}
