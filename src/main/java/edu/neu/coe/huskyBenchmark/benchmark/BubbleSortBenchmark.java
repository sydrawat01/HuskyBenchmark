package edu.neu.coe.huskyBenchmark.benchmark;

import edu.neu.coe.huskyBenchmark.sort.ComparisonSortHelper;
import edu.neu.coe.huskyBenchmark.sort.HelperFactory;
import edu.neu.coe.huskyBenchmark.sort.SortWithHelper;
import edu.neu.coe.huskyBenchmark.sort.simple.BubbleSort;
import edu.neu.coe.huskyBenchmark.util.*;

import java.io.IOException;

public class BubbleSortBenchmark {

    public BubbleSortBenchmark(int N, int runs) {
        this.N = N;
        this.runs = runs;
    }

    private void getStats() throws IOException {
        final Config config = Config.load(getClass());
        ComparisonSortHelper<Integer> helper = HelperFactory.create("BubbleSort", N, config);
        helper.init(N);

        Integer[] xs = helper.random(Integer.class, r -> r.nextInt(1000000000));

        // run timing benchmarking
        runBenchmarks(N, runs, xs);

        helper.preProcess(xs);
        SortWithHelper<Integer> sorter = new BubbleSort<>(helper);
        sorter.preProcess(xs);
        Integer[] ys = sorter.sort(xs);
        sorter.postProcess(ys);
        StatPack statPack = helper.getInstrumenter().getStatPack();
        int hits = (int) statPack.getStatistics(Instrumenter.HITS).mean();
        logger.info("Total Hits: " + hits);
    }

    private void runBenchmarks(int N, int runs, Integer[] xs) {
        System.out.println("============================================================");
        System.out.println("BubbleSort Benchmark: N=" + N);
        String description = "Bubble Sort";

        BubbleSort<Integer> bubbleSort = new BubbleSort<>();

        final double timeRandom = new Benchmark<Integer[]>(
                description + " (Random)",
                null,
                (x)->bubbleSort.sort(xs.clone(),0, xs.length),
                null
        ).run(xs, runs);
        for (TimeLogger timeLogger : timeLoggers) timeLogger.log(timeRandom, N);
    }

    private final static TimeLogger[] timeLoggers = {
            new TimeLogger("Raw time per run (mSec): ", (time, N) -> time)
    };

    public static void main(String[] args) throws IOException {
        int runs = 100;
        for (int i=7; i<15; i++) {
            int N = (int) Utilities.power2(i);
            new BubbleSortBenchmark(N, runs).getStats();
        }
    }

    private final int runs;
    private final int N;
    final static LazyLogger logger = new LazyLogger(BubbleSortBenchmark.class);
}
