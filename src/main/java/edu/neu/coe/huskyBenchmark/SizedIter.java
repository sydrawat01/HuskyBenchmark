package edu.neu.coe.huskyBenchmark;

public interface SizedIter<T> extends Iterable<T> {
    /**
     * Method to yield the size of this iterable.
     *
     * @return the size.
     */
    int size();
}
