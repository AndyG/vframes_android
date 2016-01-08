package testdata.testdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import data.model.character.SFCharacter;
import data.model.move.IFrameDataEntry;
import data.model.move.IMoveListEntry;
import data.model.move.MoveCategory;

public class CharacterFactory {

    private Random randomGenerator;

    public CharacterFactory() {
        randomGenerator = new Random();
    }

    public CharacterFactory(long randomSeed) {
        randomGenerator = new Random(randomSeed);
    }

    public CharacterFactory(Random randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    public SFCharacter generateCharacter() {
        Map<MoveCategory, List<IMoveListEntry>> moveList = generateMoveList();
        return new SFCharacter(moveList);
    }

    private Map<MoveCategory, List<IMoveListEntry>> generateMoveList() {
        Map<MoveCategory, List<IMoveListEntry>> moveList = new HashMap<>();
        MoveFactory moveFactory = new MoveFactory(randomGenerator);
        for(MoveCategory category : MoveCategory.values()) {
            List<IMoveListEntry> moves = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                moves.add(moveFactory.generateMoveListMove());
            }
            moveList.put(category, moves);
        }
        return moveList;
    }

    private Map<MoveCategory, List<IFrameDataEntry>> generateMoveset() {
        Map<MoveCategory, List<IFrameDataEntry>> moveList = new HashMap<>();
        for(MoveCategory category : MoveCategory.values()) {
            moveList.put(category, generateRandomMoves(5));
        }
        return moveList;
    }

    private List<IFrameDataEntry> generateRandomMoves(int numMoves) {
        MoveFactory moveFactory = new MoveFactory(randomGenerator);
        List<IFrameDataEntry> moves = new ArrayList<>();

        for (int i = 0; i < numMoves; i++) {
            moves.add(moveFactory.generateMove());
        }

        return moves;
    }
}
