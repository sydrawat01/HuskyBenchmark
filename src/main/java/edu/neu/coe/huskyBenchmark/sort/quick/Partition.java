package edu.neu.coe.huskyBenchmark.sort.quick;

/**
 * Class to represent a partition for Quicksort.
 *
 * @param <X> the underlying type of the array.
 */
public class Partition<X extends Comparable<X>> {
    /**
     * @param xs the array to be sorted.
     * @param from the index of the first element to be sorted.
     * @param to the index of the first element NOT to be sorted.
     */
    public Partition(X[] xs, int from, int to) {
        this.xs = xs;
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "Partition{" +
                "xs: " + xs.length + " elements" +
                ", lo=" + from +
                ", hi=" + to +
                '}';
    }

    public final X[] xs;
    public final int from;
    public final int to;
}
