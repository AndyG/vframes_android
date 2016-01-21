package data.model.character;


import java.util.List;
import java.util.Map;

import data.model.move.IMoveListEntry;
import data.model.move.MoveCategory;

public class SFCharacter {

    private Map<MoveCategory, List<IMoveListEntry>> moveListMap;
    private FrameData frameData;

    public SFCharacter(Map<MoveCategory, List<IMoveListEntry>> moveListMap, FrameData frameData) {
        this.moveListMap = moveListMap;
        this.frameData = frameData;
    }

    public Map<MoveCategory, List<IMoveListEntry>> getMoveList() {
        return moveListMap;
    }

    public FrameData getFrameData() {
        return frameData;
    }
}
