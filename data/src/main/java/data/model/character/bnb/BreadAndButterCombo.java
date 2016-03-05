package data.model.character.bnb;

public class BreadAndButterCombo {

    private String bnbLabel;
    private String bnbInputs;
    private String bnbDescription;

    public BreadAndButterCombo(String label, String inputs, String description) {
        this.bnbLabel = label;
        this.bnbInputs = inputs;
        this.bnbDescription = description;
    }

    public String getLabel() {
        return bnbLabel;
    }

    public String getInputs() {
        return bnbInputs;
    }

    public String getDescription() {
        return bnbDescription;
    }
}
