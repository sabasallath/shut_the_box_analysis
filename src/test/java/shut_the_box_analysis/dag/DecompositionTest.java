package shut_the_box_analysis.dag;

import com.google.common.collect.*;
import org.junit.Before;
import org.junit.Test;
import shut_the_box_analysis.states.CostType;
import shut_the_box_analysis.states.StateFactory;

import java.util.BitSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


public class DecompositionTest {

    private Decomposition decomposition;
    private StateFactory factory;

    @Before
    public void init() {
        factory = new StateFactory(CostType.CONCAT);
        decomposition = new Decomposition(factory);
    }


    @Test
    public void powerSetTest() {
        int n = 3;
        BitSet bs = BitSet.valueOf(new long[]{n});
        System.out.println(bs);
    }

    @Test
    public void decompositionTest() {
        ImmutableList<Set<Integer>> decomp3 = ImmutableList.of(
                        Sets.newHashSet(1, 2),
                        Sets.newHashSet(3));
        assertEquals(decomp3, decomposition.get(3));

        ImmutableList<Set<Integer>> decomp6 = ImmutableList.of(
                        Sets.newHashSet(1, 2, 3),
                        Sets.newHashSet(2, 4),
                        Sets.newHashSet(1, 5),
                        Sets.newHashSet(6));
        assertEquals(decomp6, decomposition.get(6));

        ImmutableList<Set<Integer>> decomp12 = ImmutableList.of(
                Sets.newHashSet(1, 2, 4, 5),
                Sets.newHashSet(3, 4, 5),
                Sets.newHashSet(1, 2, 3, 6),
                Sets.newHashSet(2, 4, 6),
                Sets.newHashSet(1, 5, 6),
                Sets.newHashSet(2, 3, 7),
                Sets.newHashSet(1, 4, 7),
                Sets.newHashSet(5, 7),
                Sets.newHashSet(1, 3, 8),
                Sets.newHashSet(4, 8),
                Sets.newHashSet(1, 2, 9),
                Sets.newHashSet(3, 9));
        assertEquals(decomp12, decomposition.get(12));
    }
}
