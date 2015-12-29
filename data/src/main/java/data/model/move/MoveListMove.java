package data.model.move;

import java.util.List;

import data.model.input.InputElement;

public class MoveListMove implements IMoveListMove {

    private String nameId;
    private String pretextId;
    private String posttextId;
    private String description;
    private List<InputElement> inputElementList;

    public MoveListMove(String nameId, String pretextId, String posttextId, String description, List<InputElement> inputElementList) {
        this.nameId = nameId;
        this.pretextId = pretextId;
        this.posttextId = posttextId;
        this.description = description;
        this.inputElementList = inputElementList;
    }

    @Override
    public String getNameId() {
        return nameId;
    }

    @Override
    public List<InputElement> getInput() {
        return inputElementList;
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
