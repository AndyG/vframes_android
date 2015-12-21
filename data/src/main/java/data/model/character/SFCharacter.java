package data.model.character;


import java.util.List;
import java.util.Map;

import data.model.move.IDisplayableMove;

/**
 * Created by andy on 11/30/15
 */
public class SFCharacter {
    private Map<String, List<IDisplayableMove>> moves;

    public SFCharacter(Map<String, List<IDisplayableMove>> moves) {
        this.moves = moves;
    }

    public Map<String, List<IDisplayableMove>> getMoves() {
        return moves;
    }
}
