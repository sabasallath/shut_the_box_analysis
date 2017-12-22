package shut_the_box_analysis;

import shut_the_box_analysis.dag.StrategyType;
import shut_the_box_analysis.states.CostType;

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
//        simulate(CostType.WIN_LOOSE, StrategyType.MAX);
    }

    private static void simulate(CostType concat, StrategyType strategy) {
        System.out.println("------------------------------------------\n" +
                "New Simulation with " + NB_GAME + " game\n" +
                "Strategy = " + strategy.toString() + "\n" +
                "Rule for cost = " + concat.toString());
        Dag dag = new Dag(concat, strategy);
        Simulation simulation = new Simulation(dag);

        int winningCount = 0;
        for (int i = 0; i < NB_GAME; i++) {
            boolean win = simulation.run() == dag.getLeaf();
            if (win) winningCount ++;
        }

        System.out.println("Winning Games : " + winningCount + "/" + NB_GAME);
        double ratio = ((double) winningCount / (double) NB_GAME) * 100;
        System.out.println("Winning Ratio : " + String.format("%.2f", ratio) + " %");

    }

}
