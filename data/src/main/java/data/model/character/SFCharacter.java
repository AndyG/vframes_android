package data.model.character;


import java.util.List;

import data.model.move.IDisplayableMove;

/**
 * Created by andy on 11/30/15
 */
public class SFCharacter {
    private List<IDisplayableMove> moves;

    public SFCharacter(List<IDisplayableMove> moves) {
        this.moves = moves;
    }

    public List<IDisplayableMove> getMoves() {
        return moves;
    }
}
