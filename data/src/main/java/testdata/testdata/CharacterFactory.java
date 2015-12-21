package testdata.testdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import data.model.character.SFCharacter;
import data.model.move.IDisplayableMove;
import data.model.move.MoveCategory;

/**
 * Created by andy on 11/30/15
 */
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

    public SFCharacter generateCharacter(int numMoves) {
        Map<MoveCategory, List<IDisplayableMove>> moves = generateMoveset();
        return new SFCharacter(moves);
    }

    private Map<MoveCategory, List<IDisplayableMove>> generateMoveset() {
        Map<MoveCategory, List<IDisplayableMove>> moveList = new HashMap<>();
        for(MoveCategory category : MoveCategory.values()) {
            moveList.put(category, generateRandomMoves(5));
        }
        return moveList;
    }

    private List<IDisplayableMove> generateRandomMoves(int numMoves) {
        MoveFactory moveFactory = new MoveFactory(randomGenerator);
        List<IDisplayableMove> moves = new ArrayList<>();

        for (int i = 0; i < numMoves; i++) {
            moves.add(moveFactory.generateMove());
        }

        return moves;
    }
}
