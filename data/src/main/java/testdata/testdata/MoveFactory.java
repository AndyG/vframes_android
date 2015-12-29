package testdata.testdata;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.Random;

import data.model.input.InputElement;
import data.model.input.InputParser;
import data.model.move.IMoveListMove;
import data.model.move.MoveListMove;
import data.model.move.MoveStrength;
import data.model.move.MoveType;
import data.model.move.TypicalMove;
import testdata.testdata.util.RandomUtil;

/**
 * Created by andy on 11/30/15
 */
public class MoveFactory {

    private static final int NAME_MIN_LENGTH = 5;
    private static final int NAME_MAX_LENGTH = 15;

    private static final int LABEL_MIN_LENGTH = 3;
    private static final int LABEL_MAX_LENGTH = 10;

    private static final int FRAMES_MIN_VALUE = 0;
    private static final int FRAMES_MAX_VALUE = 100;

    private static final int DAMAGE_MIN_VALUE = 0;
    private static final int DAMAGE_MAX_VALUE = 2000;

    private static final int STUN_MIN_VALUE = 0;
    private static final int STUN_MAX_VALUE = 2000;

    private Random randomGenerator;

    public MoveFactory() {
        randomGenerator = new Random();
    }

    public MoveFactory(long randomSeed) {
        randomGenerator = new Random(randomSeed);
    }

    public MoveFactory(Random randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    public TypicalMove generateMove() {
        int nameLength = RandomUtil.getRandomInt(randomGenerator, NAME_MIN_LENGTH, NAME_MAX_LENGTH);
        String name = RandomStringUtils.randomAlphabetic(nameLength);

        int labelLength = RandomUtil.getRandomInt(randomGenerator, LABEL_MIN_LENGTH, LABEL_MAX_LENGTH);
        String label = RandomStringUtils.randomAlphabetic(labelLength);

        MoveType moveType = RandomUtil.getRandomEnumValue(MoveType.class, randomGenerator);

        int startupFrames = RandomUtil.getRandomInt(randomGenerator, FRAMES_MIN_VALUE, FRAMES_MAX_VALUE);
        int activeFrames = RandomUtil.getRandomInt(randomGenerator, FRAMES_MIN_VALUE, FRAMES_MAX_VALUE);
        int recoveryFrames = RandomUtil.getRandomInt(randomGenerator, FRAMES_MIN_VALUE, FRAMES_MAX_VALUE);
        int blockstunFrames = RandomUtil.getRandomInt(randomGenerator, FRAMES_MIN_VALUE, FRAMES_MAX_VALUE);
        int hitstunFrames = RandomUtil.getRandomInt(randomGenerator, FRAMES_MIN_VALUE, FRAMES_MAX_VALUE);

        int damageValue = RandomUtil.getRandomInt(randomGenerator, DAMAGE_MIN_VALUE, DAMAGE_MAX_VALUE);
        int stunValue = RandomUtil.getRandomInt(randomGenerator, STUN_MIN_VALUE, STUN_MAX_VALUE);

        MoveStrength strength = RandomUtil.getRandomEnumValue(MoveStrength.class, randomGenerator);

        return new TypicalMove(name, label, moveType, startupFrames, activeFrames, recoveryFrames, blockstunFrames, hitstunFrames, damageValue, stunValue, strength);
    }

    public IMoveListMove generateMoveListMove() {
        int nameLength = RandomUtil.getRandomInt(randomGenerator, NAME_MIN_LENGTH, NAME_MAX_LENGTH);
        InputParser inputParser = new InputParser();
        String name = RandomStringUtils.randomAlphabetic(nameLength);
        String input = "qcf|+|lp";
        String pretextId = "id_pretext_test";
        String posttextId = "id_posttext_test";
        String descriptionId = "id_description_test";
        List<InputElement> inputElementList = inputParser.parseInputString(input);
        return new MoveListMove(name, pretextId, posttextId, descriptionId, inputElementList);
    }
}
