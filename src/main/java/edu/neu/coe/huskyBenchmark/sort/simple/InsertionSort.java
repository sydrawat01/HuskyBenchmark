/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package edu.neu.coe.huskyBenchmark.sort.simple;

import edu.neu.coe.huskyBenchmark.sort.ComparableSortHelper;
import edu.neu.coe.huskyBenchmark.sort.ComparisonSortHelper;
import edu.neu.coe.huskyBenchmark.sort.SortWithHelper;
import edu.neu.coe.huskyBenchmark.util.Config;

/**
 * Class to implement Insertion Sort.
 * NOTE: this implementation does NOT use the insertion swap mechanism,
 *
 * @param <X> the underlying type to be sorted.
 */
public class InsertionSort<X extends Comparable<X>> extends SortWithHelper<X> {
    /**
     * Sort the sub-array xs:from:to using insertion sort.
     *
     * @param xs   sort the array xs from "from" to "to".
     * @param from the index of the first element to sort
     * @param to   the index of the first element not to sort
     */
    public void sort(final X[] xs, final int from, final int to) {
        final ComparisonSortHelper<X> helper = getHelper();
        for (int i = from + 1; i < to; i++) {
            // TODO implement using swapIntoSorted
            int j = i;
            while (j > from && helper.swapStableConditional(xs, j)) j--;
        }
    }

    public static <Y extends Comparable<Y>> void mutatingInsertionSort(final Y[] ys) {
        new InsertionSort<Y>().mutatingSort(ys);
    }

    public static final String DESCRIPTION = "Insertion sort";

    /**
     * Constructor for InsertionSort
     *
     * @param N      the number elements we expect to sort.
     * @param config the configuration.
     */
    public InsertionSort(final int N, final Config config) {
        super(DESCRIPTION, N, config);
    }

    public InsertionSort() {
        this(new ComparableSortHelper<>(DESCRIPTION));
    }

    /**
     * Constructor for InsertionSort
     *
     * @param helper an explicit instance of ComparisonSortHelper to be used.
     */
    public InsertionSort(final ComparisonSortHelper<X> helper) {
        super(helper);
    }
}
