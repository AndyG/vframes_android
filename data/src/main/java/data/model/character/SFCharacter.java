package data.model.character;


import java.util.List;
import java.util.Map;

import data.model.move.IMoveListMove;
import data.model.move.MoveCategory;

public class SFCharacter {

    private Map<MoveCategory, List<IMoveListMove>> movelist_map;

    public SFCharacter(Map<MoveCategory, List<IMoveListMove>> movelist_map) {
        this.movelist_map = movelist_map;
    }

    public Map<MoveCategory, List<IMoveListMove>> getMoveList() {
        return movelist_map;
    }
}
