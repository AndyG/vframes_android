package data.model.move;

import java.util.List;

import data.model.input.InputElement;

public interface IMoveListEntry {
    String getNameId();
    List<InputElement> getInput();
    String getPretextId();
    String getPosttextId();
    String getDescriptionId();
}
