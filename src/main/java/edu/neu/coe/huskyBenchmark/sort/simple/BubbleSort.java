package edu.neu.coe.huskyBenchmark.sort.simple;

import edu.neu.coe.huskyBenchmark.sort.ComparableSortHelper;
import edu.neu.coe.huskyBenchmark.sort.ComparisonSortHelper;
import edu.neu.coe.huskyBenchmark.sort.SortWithHelper;
import edu.neu.coe.huskyBenchmark.util.Config;

import java.io.IOException;

public class BubbleSort<X extends Comparable<X>> extends SortWithHelper<X> {

    /**
     * Sort the sub-array xs:from:to using bubble sort.
     *
     * @param xs   sort the array xs from "from" to "to".
     * @param from the index of the first element to sort
     * @param to   the index of the first element not to sort
     */
    public void sort(X[] xs, int from, int to) {
        final ComparisonSortHelper<X> helper = getHelper();
        for (int j = to; j > 0; j--) {
            boolean swapped = false;
            for (int i = 1; i < j; i++) swapped |= helper.swapStableConditional(xs, i);
            if (!swapped) break;
        }
    }

    /**
     * This is used by unit tests.
     *
     * @param ys  the array to be sorted.
     * @param <Y> the underlying element type.
     */
    public static <Y extends Comparable<Y>> void mutatingBubbleSort(Y[] ys) throws IOException {
        new BubbleSort<Y>().mutatingSort(ys);
    }

    public static final String DESCRIPTION = "Bubble sort";

    /**
     * Constructor for BubbleSort
     *
     * @param N      the number elements we expect to sort.
     * @param config the configuration.
     */
    public BubbleSort(final int N, final Config config) {
        super(DESCRIPTION, N, config);
    }

    public BubbleSort() {
        this(new ComparableSortHelper<>(DESCRIPTION));
    }

    /**
     * Constructor for BubbleSort
     *
     * @param helper an explicit instance of ComparisonSortHelper to be used.
     */
    public BubbleSort(final ComparisonSortHelper<X> helper) {
        super(helper);
    }
}
