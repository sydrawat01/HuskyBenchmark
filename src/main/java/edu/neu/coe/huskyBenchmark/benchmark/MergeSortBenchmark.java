package edu.neu.coe.huskyBenchmark.benchmark;
import edu.neu.coe.huskyBenchmark.sort.ComparisonSortHelper;
import edu.neu.coe.huskyBenchmark.sort.HelperFactory;
import edu.neu.coe.huskyBenchmark.sort.SortWithHelper;
import edu.neu.coe.huskyBenchmark.sort.simple.MergeSort;

import edu.neu.coe.huskyBenchmark.util.*;
import org.ini4j.Ini;

import java.io.IOException;
public class MergeSortBenchmark {
    public MergeSortBenchmark(int n, int runs) {
        this.n = n;
        this.runs = runs;
    }

    private void getStats(int n) throws IOException {
        //final Ini ini = new Ini();
        //ini.put(Config.HELPER, "cutoff", 0);
        final Config config = Config.load(getClass());
        ComparisonSortHelper<Integer> helper = HelperFactory.create("MergeSort", n, config);
        helper.init(n);

        Integer[] xs = helper.random(Integer.class, r -> r.nextInt(10000000));

        // run timing benchmarking
        runBenchmarks(n, runs, xs,helper);
        // finish timing benchmarking
        helper.preProcess(xs);
        SortWithHelper<Integer> sorter = new MergeSort<>(helper);
        sorter.preProcess(xs);
        Integer[] ys = sorter.sort(xs);
        sorter.postProcess(ys);
        StatPack statPack = helper.getInstrumenter().getStatPack();
        int hits = (int) statPack.getStatistics(Instrumenter.HITS).mean();
        System.out.println("Hits: " + hits);
    }

    private void runBenchmarks(int n, int runs, Integer[] xs,ComparisonSortHelper<Integer> helper) throws IOException {
        System.out.println("MergeSort Benchmark: N=" + n);
        String description = "Merge Sort";


        MergeSort<Integer> mergesort = new MergeSort<>(helper);

        final double timeRandom = new Benchmark<Integer[]>(
                description + " (Random)",
                null,
                (x)->mergesort.sort(xs.clone(),0, xs.length),
                null
        ).run(xs, runs);
        for (TimeLogger timeLogger : timeLoggers) timeLogger.log(timeRandom, n);
    }

    private final static TimeLogger[] timeLoggers = {
            new TimeLogger("Raw time per run (mSec): ", (time, n) -> time)
    };

    public static void main(String[] args) throws IOException {
        int runs = 100;
        for (int i=7; i<15; i++) {
            int n = (int) Math.pow(2, i);
            new MergeSortBenchmark(n, runs).getStats(n);
        }
    }

    private final int runs;
    private final int n;
    final static LazyLogger logger = new LazyLogger(MergeSortBenchmark.class);
}
