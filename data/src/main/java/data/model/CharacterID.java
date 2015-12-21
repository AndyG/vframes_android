package data.model;

public enum CharacterID {
    RYU("ryu"),
    CHUN("chun"),
    DICTATOR("dictator"),
    BIRDIE("birdie"),
    NASH("nash"),
    CAMMY("cammy"),
    CLAW("claw"),
    LAURA("laura"),
    KEN("ken"),
    NECALLI("necalli"),
    RASHID("rashid"),
    MIKA("mika"),
    ZANGIEF("zangief"),
    FANG("fang"),
    DHALSIM("dhalsim"),
    KARIN("karin");

    private String name;

    CharacterID(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    //TODO: find out if we need this
    public static CharacterID fromString(String characterNameStr) {
        for (CharacterID characterID : values()) {
            if (characterID.name.equals(characterNameStr)) {
                return characterID;
            }
        }
        throw new IllegalArgumentException("failed to find character matching: " + characterNameStr);
    }
}
