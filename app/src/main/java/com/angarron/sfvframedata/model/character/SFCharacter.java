package com.angarron.sfvframedata.model.character;


import com.angarron.sfvframedata.model.move.IDisplayableMove;

import java.util.List;
import java.util.Map;

/**
 * Created by andy on 11/30/15
 */
public class SFCharacter {
    private String name;
    private List<IDisplayableMove> moves;

    public SFCharacter(String name, List<IDisplayableMove> moves) {
        this.name = name;
        this.moves = moves;
    }

    public String getName() {
        return name;
    }

    public List<IDisplayableMove> getMoves() {
        return moves;
    }
}
