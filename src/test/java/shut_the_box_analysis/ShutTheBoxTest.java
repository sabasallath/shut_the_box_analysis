package shut_the_box_analysis;

import com.google.common.collect.Sets;
import org.junit.Test;
import org.mutabilitydetector.internal.com.google.common.collect.Lists;
import shut_the_box_analysis.box.Box;
import shut_the_box_analysis.states.State;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ShutTheBoxTest {
    private static final double delta = 0.0001;

    @Test
    public void createTransitionTreetest() {
        ShutTheBox box = new ShutTheBox();
        System.out.println();

        HashMap<Integer, State> tt = box.getTransitionTree();
        ArrayList<Integer> l_0 = Lists.newArrayList();
        State stateLeaf = tt.get(new State(Sets.newTreeSet(l_0), 0).hashCode());
        assertEquals(0.0, stateLeaf.getCost(), delta);

        State root = tt.get(new State(Sets.newTreeSet(Box.irange())).hashCode());
        assertEquals(14764.3168, root.getCost(), delta);
    }
}
