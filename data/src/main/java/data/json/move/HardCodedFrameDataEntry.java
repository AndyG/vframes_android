package data.json.move;

import data.model.move.IFrameDataEntry;
import data.model.move.MoveType;

/**
 * Created by andy on 1/7/16
 */
public class HardCodedFrameDataEntry implements IFrameDataEntry {

    private String displayName;
    private MoveType type;

    private int startupFrames;
    private int activeFrames;
    private int recoveryFrames;
    private int blockAdvantage;
    private int hitAdvantage;

    private int damageValue;
    private int stunValue;

    private String description;

    public HardCodedFrameDataEntry(String displayName, MoveType type, int startupFrames, int activeFrames, int recoveryFrames, int blockAdvantage, int hitAdvantage, int damageValue, int stunValue, String description) {
        this.displayName = displayName;
        this.type = type;

        this.startupFrames = startupFrames;
        this.activeFrames = activeFrames;
        this.recoveryFrames = recoveryFrames;
        this.blockAdvantage = blockAdvantage;
        this.hitAdvantage = hitAdvantage;

        this.damageValue = damageValue;
        this.stunValue = stunValue;

        this.description = description;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public MoveType getMoveType() {
        return type;
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
        return blockAdvantage;
    }

    @Override
    public int getHitAdvantage() {
        return hitAdvantage;
    }

    @Override
    public int getDamageValue() {
        return damageValue;
    }

    @Override
    public int getStunValue() {
        return stunValue;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
