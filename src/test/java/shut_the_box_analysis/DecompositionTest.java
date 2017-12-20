package shut_the_box_analysis;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import shut_the_box_analysis.dag.Decomposition;
import shut_the_box_analysis.dag.states.CostType;
import shut_the_box_analysis.dag.states.State;
import shut_the_box_analysis.dag.states.StateFactory;

import java.util.BitSet;
import java.util.LinkedList;
import java.util.TreeSet;

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


    @Ignore
    @Test
    public void getValidtest() {
        TreeSet<Integer> state = Sets.newTreeSet();
        state.add(1);
        State root = factory.state(Sets.newTreeSet(state), 3);
        ImmutableList<State> valid = decomposition.getValid(root);

        LinkedList<Integer> l = Lists.newLinkedList();
        assertEquals(factory.state(Sets.newTreeSet(l)), valid);
    }
}
