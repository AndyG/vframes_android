package data.model.move;

public interface IDisplayableMove {

    int CODE_NOT_APPLICABLE = 1001;

    String getName();
    String getLabel();
    MoveType getMoveType();

    int getStartupFrames();
    int getActiveFrames();
    int getRecoveryFrames();

    int getBlockAdvantage();
    int getHitAdvantage();

    int getDamageValue();
    int getStunValue();
    MoveStrength getStrength();
}
