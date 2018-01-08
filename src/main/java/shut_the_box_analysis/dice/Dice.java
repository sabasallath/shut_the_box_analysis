package shut_the_box_analysis.dice;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class Dice {

    private final ContiguousSet<Integer> range;

    private Dice() {
        this.range = ContiguousSet.create(
                Range.closed(DiceConst.MIN.get(), DiceConst.MAX.get()),
                DiscreteDomain.integers());
    }

    private final static class DiceHolder {
        private final static Dice DICE = new Dice();
    }

    private static Dice getInstance() {
        return DiceHolder.DICE;
    }

    public static int roll() {
        return rollOneDice() + rollOneDice();
    }

    private static int rollOneDice() {
        return ThreadLocalRandom.current().nextInt(DiceConst.MINSIDE.get(), DiceConst.SIDES.get() + 1);
    }

    public static ContiguousSet<Integer> irange() {
        return getInstance().range;
    }
}