package data.model.move;

public interface IFrameDataEntryHolder {
    boolean hasAlternate();
    IFrameDataEntry getFrameDataEntry();
    IFrameDataEntry getAlternateFrameDataEntry();
}
