package shut_the_box_analysis.box;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;

public class Box {

        private final ContiguousSet<Integer> range;

        private Box() {
            this.range = ContiguousSet.create(
                    Range.closed(BoxConst.MIN.get(), BoxConst.MAX.get()),
                    DiscreteDomain.integers());
        }

        private final static class BoxHolder {
            private final static Box BOX = new Box();
        }

        private static Box getInstance() {
            return BoxHolder.BOX;
        }

        public static ContiguousSet<Integer> irange() {
            return getInstance().range;
        }

}
