package data.model.move;

import java.util.List;

import data.model.input.InputElement;

public interface IMoveListEntry {
    String getName();
    List<InputElement> getInput();
    String getPretext();
    String getPosttext();
    String getDescription();
}
