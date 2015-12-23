package data.model.move;

public class MoveListMove implements IMoveListMove {

    private String nameId;
    private String inputString;
    private String pretextId;
    private String posttextId;
    private String description;

    public MoveListMove(String nameId, String inputString, String pretextId, String posttextId, String description) {
        this.nameId = nameId;
        this.inputString = inputString;
        this.pretextId = pretextId;
        this.posttextId = posttextId;
        this.description = description;
    }

    @Override
    public String getNameId() {
        return nameId;
    }

    @Override
    public String getInputString() {
        return inputString;
    }

    @Override
    public String getPretextId() {
        return pretextId;
    }

    @Override
    public String getPosttextId() {
        return posttextId;
    }

    @Override
    public String getDescriptionId() {
        return description;
    }
}
