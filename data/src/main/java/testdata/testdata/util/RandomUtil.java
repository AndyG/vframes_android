package testdata.testdata.util;

import java.util.Random;

public class RandomUtil {

    public static int getRandomInt(Random randomGenerator, int rangeStart, int rangeEnd) {
        return randomGenerator.nextInt(rangeEnd - rangeStart + 1) + rangeStart;
    }

    public static <T extends Enum<?>> T getRandomEnumValue(Class<T> clazz, Random randomGenerator){
        int x = randomGenerator.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
}
