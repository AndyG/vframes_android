package data.model.character;


import java.util.List;
import java.util.Map;

import data.model.move.IFrameDataEntry;
import data.model.move.IMoveListEntry;
import data.model.move.MoveCategory;

public class SFCharacter {

    private Map<MoveCategory, List<IMoveListEntry>> moveListMap;
    private List<FrameData> frameData;

    public SFCharacter(Map<MoveCategory, List<IMoveListEntry>> moveListMap, List<FrameData> frameData) {
        this.moveListMap = moveListMap;
        this.frameData = frameData;
    }

    public Map<MoveCategory, List<IMoveListEntry>> getMoveList() {
        return moveListMap;
    }

    public List<FrameData> getFrameData() {
        return frameData;
    }
}
