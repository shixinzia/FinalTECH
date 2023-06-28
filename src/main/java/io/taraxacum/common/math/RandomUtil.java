package io.taraxacum.common.math;

import javax.annotation.Nonnull;
import java.util.Random;

public class RandomUtil {

    public static boolean compareTwoRandom(@Nonnull Random random, double a1, double a2) {
        return random.nextDouble(a1) < random.nextDouble(a2);
    }
}
