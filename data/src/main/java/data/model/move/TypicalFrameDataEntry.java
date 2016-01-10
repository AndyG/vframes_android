package data.model.move;

public class TypicalFrameDataEntry implements IFrameDataEntry {

    private String name;
    private MoveType moveType;

    private int startupFrames;
    private int activeFrames;
    private int recoveryFrames;

    private int blockstunFrames;
    private int hitstunFrames;

    private int damageValue;
    private int stunValue;

    public TypicalFrameDataEntry(String name, MoveType moveType, int startupFrames,
                                 int activeFrames, int recoveryFrames, int blockstunFrames, int hitstunFrames,
                                 int damageValue, int stunValue) {

        this.name = name;
        this.moveType = moveType;

        this.startupFrames = startupFrames;
        this.activeFrames = activeFrames;
        this.recoveryFrames = recoveryFrames;

        this.blockstunFrames = blockstunFrames;
        this.hitstunFrames = hitstunFrames;

        this.damageValue = damageValue;
        this.stunValue = stunValue;
    }

    @Override
    public String getDisplayName() {
        return name;
    }

    @Override
    public MoveType getMoveType() {
        return moveType;
    }

    @Override
    public int getStartupFrames() {
        return startupFrames;
    }

    @Override
    public int getActiveFrames() {
        return activeFrames;
    }

    @Override
    public int getRecoveryFrames() {
        return recoveryFrames;
    }

    @Override
    public int getBlockAdvantage() {
        return blockstunFrames - (recoveryFrames + activeFrames - 1);
    }

    @Override
    public int getHitAdvantage() {
        return hitstunFrames - (recoveryFrames + activeFrames - 1);
    }

    @Override
    public int getDamageValue() {
        return damageValue;
    }

    @Override
    public int getStunValue() {
        return stunValue;
    }

    public int getBlockstunFrames() {
        return blockstunFrames;
    }

    public int getHitstunFrames() {
        return hitstunFrames;
    }
}
