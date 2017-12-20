package shut_the_box_analysis.dag;

import com.google.common.collect.*;
import shut_the_box_analysis.box.Box;
import shut_the_box_analysis.dag.states.StateFactory;
import shut_the_box_analysis.dice.Dice;
import shut_the_box_analysis.dag.states.State;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Decomposition {

    private final StateFactory stateFactory;
    private ImmutableListMultimap<Integer, Set<Integer>> imap;

    public Decomposition(StateFactory stateFactory) {
        this.stateFactory = stateFactory;

        List<IntStream> lIntStream = Sets.powerSet(Sets.newHashSet(Box.irange()))
                .stream()
                .filter(e -> Dice.irange().range().contains(
                        e.stream().mapToInt(Integer::intValue).sum()))
                .map(e -> e.stream().mapToInt(Integer::intValue))
                .collect(Collectors.toList());

        ListMultimap<Integer, Set<Integer>> mmap = MultimapBuilder.hashKeys().linkedListValues().build();

        for (IntStream stream : lIntStream) {
            Set<Integer> il = stream.boxed().collect(Collectors.toSet());
            int sum = il.stream().mapToInt(Integer::intValue).sum();
            mmap.put(sum, il);
        }

        imap = ImmutableListMultimap.copyOf(mmap);
    }

    public ImmutableList<Set<Integer>> get(int i) {
        return imap.get(i);
    }


    public ImmutableList<State> getValid(State current) {
        List<State> res = new LinkedList<>();
        Set<Integer> currentState = current.getState();

        for (Set<Integer> decomposition : get(current.dice())) {
            if (isValidDecomposition(currentState, decomposition)) {
                TreeSet<Integer> decompSet = Sets.newTreeSet(currentState);
                decompSet.removeAll(decomposition);
                res.add(stateFactory.state(decompSet));
            }
        }

        return ImmutableList.copyOf(res);
    }

    private boolean isValidDecomposition(Set<Integer> currentState, Set<Integer> decomposition) {
        return currentState.containsAll(decomposition);
    }

}