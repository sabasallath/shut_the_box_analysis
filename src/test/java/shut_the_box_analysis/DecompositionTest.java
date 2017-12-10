package shut_the_box_analysis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;
import shut_the_box_analysis.Decomposition;
import shut_the_box_analysis.states.State;

import java.util.LinkedList;
import java.util.TreeSet;


public class DecompositionTest {

    public static Decomposition d = new Decomposition();

//    @Test
//    public void powerSetTest() {
//        int n = 3;
//        BitSet bs = BitSet.valueOf(new long[]{n});
//        System.out.println(bs);
//    }
//

    @Test
    public void getValidtest() {
        TreeSet<Integer> state = Sets.newTreeSet();
        state.add(1);
        State root = new State(Sets.newTreeSet(state), 3);
        ImmutableList<State> valid = d.getValid(root);

        LinkedList<Integer> l = Lists.newLinkedList();
        l.add(1);
        l.add(3);

        assertEquals(new State(Sets.newTreeSet(l)), valid.get(0));
    }
}
