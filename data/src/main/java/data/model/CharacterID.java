package data.model;

public enum CharacterID {
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
}
