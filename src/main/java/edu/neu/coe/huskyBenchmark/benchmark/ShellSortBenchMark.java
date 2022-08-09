package edu.neu.coe.huskyBenchmark.benchmark;

import edu.neu.coe.huskyBenchmark.sort.ComparisonSortHelper;
import edu.neu.coe.huskyBenchmark.sort.HelperFactory;
import edu.neu.coe.huskyBenchmark.sort.SortWithHelper;
import edu.neu.coe.huskyBenchmark.sort.simple.ShellSort;
import edu.neu.coe.huskyBenchmark.util.*;

import java.io.IOException;

public class ShellSortBenchMark {
    public ShellSortBenchMark(int N, int runs) {
        this.N = N;
        this.runs = runs;
    }

    private void runBenchmarking() throws IOException {
        final Config config = Config.load(getClass());

        System.out.println("============================================================");
        System.out.println("ShellSort Benchmark: N=" + N);
        String description = "Shell Sort";

        ComparisonSortHelper<Integer> helper = HelperFactory.create(description, N, config);
        helper.init(N);

        Integer[] xs = helper.random(Integer.class, r -> r.nextInt(1000));
        helper.preProcess(xs);
        SortWithHelper<Integer> sorter = new ShellSort<>(3,helper);

        final double time = new Benchmark<>(
                description + " (Random)",
                (x)->sorter.preProcess(xs),
                (x)->sorter.sortArray(xs),
                sorter::postProcess
        ).run(xs, runs);
        for (TimeLogger timeLogger : timeLoggers) timeLogger.log(time, N);

        StatPack statPack = helper.getInstrumenter().getStatPack();
        int hits = (int) statPack.getStatistics(Instrumenter.HITS).mean();
        logger.info("Total Hits: " + hits);
    }

    private final static TimeLogger[] timeLoggers = {
            new TimeLogger("Raw time per run (mSec): ", (time, N) -> time)
    };


    public static void main(String[] args) throws IOException {
        int runs = 1000;
        for (int i=7; i<19; i++) {
            int N = (int) Math.pow(2, i);
            new ShellSortBenchMark(N, runs).runBenchmarking();
        }
    }
    private final int runs;
    private final int N;
    final static LazyLogger logger = new LazyLogger(ShellSortBenchMark.class);
}
