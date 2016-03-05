package data.model.move;

public interface IFrameDataEntry {

    int CODE_NOT_APPLICABLE = 1001;

    String getDisplayName();
    MoveType getMoveType();

    int getStartupFrames();
    int getActiveFrames();
    int getRecoveryFrames();

    int getBlockAdvantage();
    int getHitAdvantage();

    int getDamageValue();
    int getStunValue();

    String getDescription();
}
