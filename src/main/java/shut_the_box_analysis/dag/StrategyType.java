package shut_the_box_analysis.dag;

import shut_the_box_analysis.dag.states.State;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public enum StrategyType {
    MIN("min"),
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

    public Function<State, Optional<State>> get() {
        switch (this) {
            case MIN:
                return state -> state.getNext().stream()
                        .min(Comparator.comparingDouble(State::getCost));
            case MAX:
                return state -> state.getNext().stream()
                        .max(Comparator.comparingDouble(State::getCost));
            case RANDOM:
                return state -> {
                    List<State> l = state.getNext();
                    Collections.shuffle(l);
                    return l.stream().findFirst();
                };
        }
        throw new RuntimeException("Should not happen");
    }


}
