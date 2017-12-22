package shut_the_box_analysis.dice;

import org.junit.Test;
import shut_the_box_analysis.TestConst;

import static org.junit.Assert.assertEquals;

public class DiceProbabilityTest {

    private static final double DELTA_DICE = 0.01;

    @Test
    public void diceProbabilityTest() {
        assertEquals(6, DiceConst.SIDES.get());
        assertEquals(2, DiceConst.MIN.get());
        assertEquals(12, DiceConst.MAX.get());
        assertEquals((1.0 / 36.0), DiceProbability.get(2), TestConst.DELTA);
        assertEquals((2.0 / 36.0), DiceProbability.get(3), TestConst.DELTA);
        assertEquals((3.0 / 36.0), DiceProbability.get(4), TestConst.DELTA);
        assertEquals((4.0 / 36.0), DiceProbability.get(5), TestConst.DELTA);
        assertEquals((5.0 / 36.0), DiceProbability.get(6), TestConst.DELTA);
        assertEquals((6.0 / 36.0), DiceProbability.get(7), TestConst.DELTA);
        assertEquals((5.0 / 36.0), DiceProbability.get(8), TestConst.DELTA);
        assertEquals((4.0 / 36.0), DiceProbability.get(9), TestConst.DELTA);
        assertEquals((3.0 / 36.0), DiceProbability.get(10), TestConst.DELTA);
        assertEquals((2.0 / 36.0), DiceProbability.get(11), TestConst.DELTA);
        assertEquals((1.0 / 36.0), DiceProbability.get(12), TestConst.DELTA);
    }

    @Test
    public void diceProbabilitySimulation() {
        int min = DiceConst.MIN.get();
        int[] simuleProb = new int[DiceConst.ELEMENTS.get()];
        int nb_game = 1000000;
        for (int i = 0; i < nb_game; i++) {
            simuleProb[Dice.roll() - min]++;
        }

        for (int i = 0; i < simuleProb.length; i++) {
            double theorical = DiceProbability.get(i + min);
            double realValue = (double) simuleProb[i] / (double) nb_game;
            assertEquals(theorical, realValue, 0.01);
        }
    }
}
