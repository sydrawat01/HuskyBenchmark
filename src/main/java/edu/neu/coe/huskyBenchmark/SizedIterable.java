package edu.neu.coe.huskyBenchmark;

import java.util.Collection;
import java.util.Iterator;

public class SizedIterable<T> implements SizedIter<T> {
    public SizedIterable(Collection<T> collection) {
        this.iterable = collection;
        size = collection.size();
    }

    public SizedIterable(Iterable<T> iterable) {
        this.iterable = iterable;
        size = getSize(iterable);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return iterable.iterator();
    }

    public static <T> SizedIterable<T> create(Collection<T> collection) {
        return new SizedIterable<>(collection);
    }

    public static <T> SizedIterable<T> create(Iterable<T> iterable) {
        return new SizedIterable<>(iterable);
    }

    public static <T> int getSize(Iterator<T> iterator) {
        int size = 0;
        while (iterator.hasNext()) {
            size++;
            iterator.next();
        }
        return size;
    }

    private final Iterable<T> iterable;
    private final int size;

    private static <T> int getSize(Iterable<T> iterable) {
        int size = 0;
        for (T t : iterable) size++;
        return size;
    }
}
