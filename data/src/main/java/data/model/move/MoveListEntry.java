package data.model.move;

import java.util.List;

import data.model.input.InputElement;

public class MoveListEntry implements IMoveListEntry {

    private String name;
    private String pretext;
    private String posttext;
    private String description;
    private List<InputElement> inputElementList;

    public MoveListEntry(String name, String pretext, String posttext, String description, List<InputElement> inputElementList) {
        this.name = name;
        this.pretext = pretext;
        this.posttext = posttext;
        this.description = description;
        this.inputElementList = inputElementList;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<InputElement> getInput() {
        return inputElementList;
    }

    @Override
    public String getPretext() {
        return pretext;
    }

    @Override
    public String getPosttext() {
        return posttext;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
