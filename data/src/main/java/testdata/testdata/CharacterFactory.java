package testdata.testdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import data.model.character.SFCharacter;
import data.model.move.IDisplayableMove;

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

        List<IDisplayableMove> moves = new ArrayList<>();
        MoveFactory moveFactory = new MoveFactory(randomGenerator);
        for (int i = 0; i < numMoves; i++) {
            moves.add(moveFactory.generateMove());
        }

        return new SFCharacter(moves);
    }
}
