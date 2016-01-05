package data.model.move;

public abstract class BaseDisplayableMove implements IDisplayableMove {

    private String name;
    private String label;
    private MoveType moveType;

    public BaseDisplayableMove (String name, String label, MoveType moveType) {
        this.name = name;
        this.label = label;
        this.moveType = moveType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public MoveType getMoveType() {
        return moveType;
    }
}
