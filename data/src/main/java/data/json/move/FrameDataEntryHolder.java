package data.json.move;

import data.model.move.IFrameDataEntry;
import data.model.move.IFrameDataEntryHolder;

public class FrameDataEntryHolder implements IFrameDataEntryHolder {

    private IFrameDataEntry frameDataEntry;
    private IFrameDataEntry alternateFrameDataEntry;

    public FrameDataEntryHolder(IFrameDataEntry frameDataEntry, IFrameDataEntry alternateFrameDataEntry) {
        this.frameDataEntry = frameDataEntry;
        this.alternateFrameDataEntry = alternateFrameDataEntry;
    }

    @Override
    public boolean hasAlternate() {
        return alternateFrameDataEntry != null;
    }

    @Override
    public IFrameDataEntry getFrameDataEntry() {
        return frameDataEntry;
    }

    @Override
    public IFrameDataEntry getAlternateFrameDataEntry() {
        if (alternateFrameDataEntry != null) {
            return alternateFrameDataEntry;
        } else {
            throw new IllegalStateException("alternateFrameDataEntry was null");
        }
    }
}
