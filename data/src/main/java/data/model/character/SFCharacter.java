package data.model.character;


import java.util.List;
import java.util.Map;

import data.model.move.IDisplayableMove;
import data.model.move.MoveCategory;

/**
 * Created by andy on 11/30/15
 */
public class SFCharacter {
    private Map<MoveCategory, List<IDisplayableMove>> moves;

    public SFCharacter(Map<MoveCategory, List<IDisplayableMove>> moves) {
        this.moves = moves;
    }

    public Map<MoveCategory, List<IDisplayableMove>> getMoves() {
        return moves;
    }
}
