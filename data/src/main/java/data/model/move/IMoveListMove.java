package data.model.move;

import java.util.List;

import data.model.input.InputElement;

public interface IMoveListMove {
    String getNameId();
    List<InputElement> getInput();
    String getPretextId();
    String getPosttextId();
    String getDescriptionId();
}
