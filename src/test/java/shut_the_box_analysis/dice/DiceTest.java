package shut_the_box_analysis.dice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class DiceTest {

    private static final int NB_ROLL = 1000;

    @Test
    public void diceRollTest() {
        int min = DiceConst.MIN.get();
        int max = DiceConst.MAX.get();
        int[] usedDiceValue = new int[DiceConst.ELEMENTS.get()];

        Arrays.fill(usedDiceValue, 0);

        for (int i = 0; i < NB_ROLL; i++) {
            int r = Dice.roll();
            assertTrue(r >= min && r <= max);
            usedDiceValue[r - min] += 1;
        }

        for (int i : usedDiceValue) {
            assertNotEquals(0, i);
        }
    }

    @Test
    public void diceRangeTest() {
        Assert.assertEquals(DiceConst.ELEMENTS.get(), Dice.irange().size());
        assertEquals(Dice.irange().range().lowerEndpoint().intValue(), DiceConst.MIN.get());
        assertEquals(Dice.irange().range().upperEndpoint().intValue(), DiceConst.MAX.get());
    }

}
