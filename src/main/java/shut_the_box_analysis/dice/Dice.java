package shut_the_box_analysis.dice;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import java.util.Random;

public final class Dice {

    private final Random random;
    private final ContiguousSet<Integer> range;

    private Dice() {
        this.random = new Random();
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
        return getInstance()
                .random.nextInt(DiceConst.ELEMENTS.get())
                + DiceConst.MIN.get();
    }

    public static ContiguousSet<Integer> irange() {
        return getInstance().range;
    }
}