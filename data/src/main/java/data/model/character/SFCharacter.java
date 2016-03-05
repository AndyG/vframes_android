package data.model.character;


import java.util.List;
import java.util.Map;

import data.model.character.bnb.BreadAndButterModel;
import data.model.move.IMoveListEntry;
import data.model.move.MoveCategory;

public class SFCharacter {

    private Map<MoveCategory, List<IMoveListEntry>> moveListMap;
    private FrameData frameData;
    private BreadAndButterModel breadAndButters;

    public SFCharacter(Map<MoveCategory, List<IMoveListEntry>> moveListMap, FrameData frameData, BreadAndButterModel breadAndButters) {
        this.moveListMap = moveListMap;
        this.frameData = frameData;
        this.breadAndButters = breadAndButters;
    }

    public Map<MoveCategory, List<IMoveListEntry>> getMoveList() {
        return moveListMap;
    }

    public FrameData getFrameData() {
        return frameData;
    }

    public BreadAndButterModel getBreadAndButters() {
        return breadAndButters;
    }
}
