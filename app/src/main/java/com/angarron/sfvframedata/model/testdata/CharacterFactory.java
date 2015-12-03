package com.angarron.sfvframedata.model.testdata;

import com.angarron.sfvframedata.model.character.SFCharacter;
import com.angarron.sfvframedata.model.move.IDisplayableMove;
import com.angarron.sfvframedata.model.testdata.util.RandomUtil;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by andy on 11/30/15
 */
public class CharacterFactory {

    private static final int NAME_MIN_LENGTH = 2;
    private static final int NAME_MAX_LENGTH = 10;

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
        int nameLength = RandomUtil.getRandomInt(randomGenerator, NAME_MIN_LENGTH, NAME_MAX_LENGTH);
        String name = RandomStringUtils.randomAlphabetic(nameLength);

        List<IDisplayableMove> moves = new ArrayList<>();
        MoveFactory moveFactory = new MoveFactory(randomGenerator);
        for (int i = 0; i < numMoves; i++) {
            moves.add(moveFactory.generateMove());
        }

        return new SFCharacter(name, moves);
    }
}
