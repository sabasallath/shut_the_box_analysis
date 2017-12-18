package shut_the_box_analysis.dag;

import shut_the_box_analysis.dag.states.State;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public enum StrategyType {
    OPTIMAL("optimal"),
    MAX("max"),
    RANDOM("random");

    private final String s;

    StrategyType(String s) {
        this.s = s;
    }

    @Override
    public String toString(){
        return s;
    }

    public Function<State, State> get() {
        switch (this) {
            case OPTIMAL:
                return state -> state.getNext().stream()
                        .min(Comparator.comparingDouble(State::getCost))
                        .orElseThrow(() -> new RuntimeException("No next - should never happen."));
            case MAX:
                return state -> state.getNext().stream()
                        .max(Comparator.comparingDouble(State::getCost))
                        .orElseThrow(() -> new RuntimeException("No next - should never happen."));
            case RANDOM:
                return state -> {
                    List<State> l = state.getNext();
                    Collections.shuffle(l);
                    return l.stream().findFirst().orElseThrow(() -> new RuntimeException("No next - should never happen."));
                };
        }
        throw new RuntimeException("Should not happen");
    }


}
