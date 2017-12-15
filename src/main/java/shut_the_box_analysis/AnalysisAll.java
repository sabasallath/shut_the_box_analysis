package shut_the_box_analysis;

import shut_the_box_analysis.dag.states.CostType;
import shut_the_box_analysis.dag.Dag;

public class AnalysisAll {

    public static void main(String[] args) {
        Dag dagSum = new Dag(CostType.SUM);
        Dag dagConcat = new Dag(CostType.CONCAT);
        new AnalysisWinningDepth(dagSum);
        new AnalysisWinningDepth(dagConcat);
    }

}
