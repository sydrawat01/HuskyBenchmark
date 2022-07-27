package edu.neu.coe.huskyBenchmark.util;

import edu.neu.coe.huskyBenchmark.sort.ComparableSortHelper;
import edu.neu.coe.huskyBenchmark.sort.ComparisonSortHelper;

import java.util.Random;

public class Shuffle<T extends Comparable<T>> {
    public Shuffle(int seed) {
        a = new Integer[seed];
        for(int i=0;i<seed; i++) {
            a[i] = i;
        }
    }

    public Integer[] supply() {
        int n = a.length;
        Random random = new Random();
        for(int i=0; i<n; i++) {
            int r = random.nextInt(i+1);
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
        return a;
    }

    private final Integer[] a;

}
