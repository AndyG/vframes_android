package data.model.character;


import java.util.List;
import java.util.Map;

import data.model.move.IMoveListEntry;
import data.model.move.MoveCategory;

public class SFCharacter {

    private Map<MoveCategory, List<IMoveListEntry>> movelist_map;

    public SFCharacter(Map<MoveCategory, List<IMoveListEntry>> movelist_map) {
        this.movelist_map = movelist_map;
    }

    public Map<MoveCategory, List<IMoveListEntry>> getMoveList() {
        return movelist_map;
    }
}
