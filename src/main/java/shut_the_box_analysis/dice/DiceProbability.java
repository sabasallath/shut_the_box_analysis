package shut_the_box_analysis.dice;

import com.google.common.collect.ImmutableMap;
import static com.google.common.collect.Maps.newHashMap;

import java.util.Arrays;
import java.util.HashMap;


public final class DiceProbability {

    private static final int sides = DiceConst.SIDES.get();
    private final ImmutableMap<Integer, Double> stat;

    private DiceProbability() {
        int[][] sum2D = new int[sides][sides];
        for (int i = 0; i < sides; i++) {
            for (int j = 0; j < sides; j++) {
                sum2D[i][j] = i + j;
            }
        }

        int[] iStat = new int[DiceConst.MAX.get() - DiceConst.MIN.get() + 1];
        Arrays.fill(iStat, 0);
        for (int i = 0; i < sides; i++) {
            for (int j = 0; j < sides; j++) {
                iStat[sum2D[i][j]] += 1;
            }
        }

        HashMap<Integer, Double> mStat = newHashMap();
        for (int i = 0; i < iStat.length; i++) {
            mStat.put(i + DiceConst.MIN.get(),  iStat[i] / Math.pow(sides, 2));
        }

        this.stat = ImmutableMap.copyOf(mStat);
    }

    private static class DiceProbabilityHolder {
        private static final DiceProbability DICE_PROB = new DiceProbability();

    }

    private static DiceProbability getInstance() {
        return DiceProbabilityHolder.DICE_PROB;
    }

    public static double get(int i) {
        return getInstance().stat.get(i);
    }
}