package shut_the_box_analysis;

import shut_the_box_analysis.dag.states.CostType;
import shut_the_box_analysis.dag.Dag;

public class Simulate {

    private final static int NB_GAME = 1000;

    public static void main(String[] args) {
        simulate(CostType.CONCAT);
        simulate(CostType.SUM);
    }

    private static void simulate(CostType concat) {
        System.out.println("New Simulation with " + NB_GAME + " game, rule for cost = " + concat.toString());
        Simulation simulation = new Simulation(new Dag(concat));

        int winningCount = 0;
        for (int i = 0; i < NB_GAME; i++) {
            if (simulation.run(false)) winningCount ++;
        }
        System.out.println("Winning Games : " + winningCount + "/" + NB_GAME);
        double ratio = ((double) winningCount / (double) NB_GAME) * 100;
        System.out.println("Winning Ratio : " + String.format("%.2f", ratio) + " %");
    }

}
