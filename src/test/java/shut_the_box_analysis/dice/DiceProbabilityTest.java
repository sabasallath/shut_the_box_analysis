package shut_the_box_analysis.dice;

import org.junit.Test;
import shut_the_box_analysis.dice.DiceConst;
import shut_the_box_analysis.dice.DiceProbability;

import static org.junit.Assert.assertEquals;

public class DiceProbabilityTest {

    private static final double DELTA = 0.0001;

    @Test
    public void diceProbabilityTest() {
        assertEquals(6, DiceConst.SIDES.get());
        assertEquals(2, DiceConst.MIN.get());
        assertEquals(12, DiceConst.MAX.get());
        assertEquals((1.0 / 36.0), DiceProbability.get(2), DELTA);
        assertEquals((2.0 / 36.0), DiceProbability.get(3), DELTA);
        assertEquals((3.0 / 36.0), DiceProbability.get(4), DELTA);
        assertEquals((4.0 / 36.0), DiceProbability.get(5), DELTA);
        assertEquals((5.0 / 36.0), DiceProbability.get(6), DELTA);
        assertEquals((6.0 / 36.0), DiceProbability.get(7), DELTA);
        assertEquals((5.0 / 36.0), DiceProbability.get(8), DELTA);
        assertEquals((4.0 / 36.0), DiceProbability.get(9), DELTA);
        assertEquals((3.0 / 36.0), DiceProbability.get(10), DELTA);
        assertEquals((2.0 / 36.0), DiceProbability.get(11), DELTA);
        assertEquals((1.0 / 36.0), DiceProbability.get(12), DELTA);
    }
}
