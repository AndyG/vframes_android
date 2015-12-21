package testdata.testdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import data.model.character.SFCharacter;
import data.model.move.IDisplayableMove;

/**
 * Created by andy on 11/30/15
 */
public class CharacterFactory {

    private static final String[] categories = {"Normals", "Specials", "V-Moves"};

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

        Map<String, List<IDisplayableMove>> moves = generateMoveset(randomGenerator);
        return new SFCharacter(moves);
    }

    private Map<String, List<IDisplayableMove>> generateMoveset(Random randomGenerator) {
        Map<String, List<IDisplayableMove>> moveList = new HashMap<>();
        for(String categoryName : categories) {
            moveList.put(categoryName, generateRandomMoves(5));
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
