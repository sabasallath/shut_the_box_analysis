package shut_the_box_analysis;

import shut_the_box_analysis.dag.StrategyType;
import shut_the_box_analysis.dag.states.CostType;
import shut_the_box_analysis.dag.states.State;

import java.util.HashMap;

import shut_the_box_analysis.dag.Dag;
import shut_the_box_analysis.simulation.Simulation;

public class Simulate {

    private final static int NB_GAME = 1000;

    public static void main(String[] args) {
        for (CostType costType : CostType.values()) {
            for (StrategyType strategy : StrategyType.values()) {
                simulate(costType, strategy);
            }
        }
//        simulate(CostType.WIN_LOOSE, StrategyType.MAX);
//        simulate(CostType.WIN_LOOSE, StrategyType.MIN);
//        simulate(CostType.WIN_LOOSE, StrategyType.RANDOM);
//        simulate(CostType.SUM, StrategyType.MIN);
    }

    private static void simulate(CostType concat, StrategyType strategy) {
        System.out.println("------------------------------------------\n" +
                "New Simulation with " + NB_GAME + " game\n" +
                "Strategy = " + strategy.toString() + "\n" +
                "Rule for cost = " + concat.toString());
        Dag dag = new Dag(concat, strategy);
        Simulation simulation = new Simulation(dag);
        HashMap<State, Integer> finalStates = new HashMap<>();
        
        int winningCount = 0;
        for (int i = 0; i < NB_GAME; i++) {
        	State finalState = simulation.run(false);
        	if(finalStates.containsKey(finalState)){
        		finalStates.replace(finalState,finalStates.get(finalState) + 1 );
        	}else{
        		finalStates.put(finalState, 1);
        	}
        	
            //boolean win = finalState == dag.getLeaf();
            //if (win) winningCount ++;
        }
        
        winningCount = finalStates.get(dag.getLeaf());
        
        System.out.println("Winning Games : " + winningCount + "/" + NB_GAME);
        double ratio = ((double) winningCount / (double) NB_GAME) * 100;
        System.out.println("Winning Ratio : " + String.format("%.2f", ratio) + " %");
        
        //System.out.println(finalStates);
       
        
    }

}
