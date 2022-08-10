package edu.neu.coe.huskyBenchmark.benchmark;

import edu.neu.coe.huskyBenchmark.sort.ComparisonSortHelper;
import edu.neu.coe.huskyBenchmark.sort.HelperFactory;
import edu.neu.coe.huskyBenchmark.sort.SortWithHelper;
import edu.neu.coe.huskyBenchmark.sort.quick.QuickSortBasic;
import edu.neu.coe.huskyBenchmark.util.*;

import java.io.IOException;

public class QuickSortBenchmark {

    public QuickSortBenchmark(int N, int runs ) {
        this.N = N;
        this.runs = runs;
    }

    private void getStats() throws IOException {
        final Config config = Config.load(getClass());
        ComparisonSortHelper<Integer> helper = HelperFactory.create("QuickSort basic", N, config);
        helper.init(N);

        Integer[] xs = helper.random(Integer.class, r -> r.nextInt(1000000000));

        // run timing benchmarking
        runBenchmarks(N, runs, xs, config);

        helper.preProcess(xs);
        SortWithHelper<Integer> sorter = new QuickSortBasic<>(helper);
        sorter.preProcess(xs);
        Integer[] ys = sorter.sort(xs);
        sorter.postProcess(ys);
        StatPack statPack = helper.getInstrumenter().getStatPack();
        int hits = (int) statPack.getStatistics(Instrumenter.HITS).mean();
        double compares =  statPack.getStatistics(Instrumenter.COMPARES).mean();
        double swaps =  statPack.getStatistics(Instrumenter.SWAPS).mean();
        logger.info("Hits: " + hits + ", Compares: " + compares + ", Swaps: " + swaps);
        String ratio = Utilities.formatDecimal3Places(swaps/compares);
        logger.info("Swaps/Compares: " + ratio);
    }

    private void runBenchmarks(int N, int runs, Integer[] xs, final Config config) {
        System.out.println("============================================================");
        System.out.println("QuickSort Benchmark: N=" + N);
        String description = "Quick Sort";

        QuickSortBasic<Integer> quickSort = new QuickSortBasic<>(N, config);

        final double timeRandom = new Benchmark<Integer[]>(
                description + " (Random)",
                null,
                (x)->quickSort.sort(xs.clone()),
                null
        ).run(xs, runs);
        for (TimeLogger timeLogger : timeLoggers) timeLogger.log(timeRandom, N);
    }

    private final static TimeLogger[] timeLoggers = {
            new TimeLogger("Raw time per run (mSec): ", (time, N) -> time)
    };

    public static void main(String[] args) throws IOException {
        int runs = 1000;
        for (int i=7; i<16; i++) {
            int N = (int) Utilities.power2(i);
            new QuickSortBenchmark(N, runs).getStats();
        }
    }

    private final int runs;
    private final int N;
    final static LazyLogger logger = new LazyLogger(QuickSortBenchmark.class);
}
