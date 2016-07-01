package data.model;

public enum CharacterID {
    BOXER("boxer"),
    IBUKI("ibuki"),
    GUILE("guile"),
    ALEX("alex"),
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

    public static CharacterID getCharacterFromString(String characterString) {
        for (CharacterID characterID : CharacterID.values()) {
            if (characterID.toString().equals(characterString)) {
                return characterID;
            }
        }
        throw new RuntimeException("could not find character for string: " + characterString);
    }
}
