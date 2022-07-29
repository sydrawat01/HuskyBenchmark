package edu.neu.coe.huskyBenchmark.benchmark;

import edu.neu.coe.huskyBenchmark.sort.ComparisonSortHelper;
import edu.neu.coe.huskyBenchmark.sort.HelperFactory;
import edu.neu.coe.huskyBenchmark.sort.SortWithHelper;
import edu.neu.coe.huskyBenchmark.sort.quick.QuickSortBasic;
import edu.neu.coe.huskyBenchmark.util.*;

import java.io.IOException;

public class QuickSortBenchmark {

    public QuickSortBenchmark(int n, int runs ) {
        this.n = n;
        this.runs = runs;
    }

    private void getStats(int n) throws IOException {
        final Config config = Config.load(getClass());
        ComparisonSortHelper<Integer> helper = HelperFactory.create("QuickSort basic", n, config);
        helper.init(n);

        Integer[] xs = helper.random(Integer.class, r -> r.nextInt(10000000));

        // run timing benchmarking
        runBenchmarks(n, runs, xs, config);
        // finish timing benchmarking
        helper.preProcess(xs);
        SortWithHelper<Integer> sorter = new QuickSortBasic<>(helper);
        sorter.preProcess(xs);
        Integer[] ys = sorter.sort(xs);
        sorter.postProcess(ys);
        StatPack statPack = helper.getInstrumenter().getStatPack();
        int hits = (int) statPack.getStatistics(Instrumenter.HITS).mean();
        double compares =  statPack.getStatistics(Instrumenter.COMPARES).mean();
        double swaps =  statPack.getStatistics(Instrumenter.SWAPS).mean();
        System.out.println("Hits: " + hits + ", Compares: " + compares + ", Swaps: " + swaps);
        double ratio = Math.round((swaps/compares) * 1000.0) / 1000.0;
        System.out.println("Swaps/Compares: " + ratio);
    }

    private void runBenchmarks(int n, int runs, Integer[] xs, final Config config) {
        System.out.println("QuickSort Benchmark: N=" + n);
        String description = "Quick Sort";

        QuickSortBasic<Integer> quickSort = new QuickSortBasic<>(n, config);

        final double timeRandom = new Benchmark<Integer[]>(
                description + " (Random)",
                null,
                (x)->quickSort.sort(xs.clone()),
                null
        ).run(xs, runs);
        for (TimeLogger timeLogger : timeLoggers) timeLogger.log(timeRandom, n);
    }

    private final static TimeLogger[] timeLoggers = {
            new TimeLogger("Raw time per run (mSec): ", (time, n) -> time)
    };

    public static void main(String[] args) throws IOException {
        int runs = 1000;
        for (int i=7; i<19; i++) {
            int n = (int) Math.pow(2, i);
            new QuickSortBenchmark(n, runs).getStats(n);
        }
    }

    private final int runs;
    private final int n;
    final static LazyLogger logger = new LazyLogger(QuickSortBenchmark.class);
}
