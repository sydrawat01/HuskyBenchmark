package edu.neu.coe.huskyBenchmark.benchmark;

import edu.neu.coe.huskyBenchmark.sort.ComparisonSortHelper;
import edu.neu.coe.huskyBenchmark.sort.HelperFactory;
import edu.neu.coe.huskyBenchmark.sort.SortWithHelper;
import edu.neu.coe.huskyBenchmark.sort.simple.ShellSort;
import edu.neu.coe.huskyBenchmark.util.*;
import java.util.Collections;

import java.io.IOException;
import java.util.Arrays;

public class ShellSortBenchMark {

    public ShellSortBenchMark(int N, int runs) {
        this.N = N;
        this.runs = runs;
    }

    private void getStats(int h, String sortType) throws IOException {
        final Config config = Config.load(getClass());
        ComparisonSortHelper<Integer> helper = HelperFactory.create("ShellSort", N, config);
        helper.init(N);

        Integer[] xs = helper.random(Integer.class, r -> r.nextInt(1000000));

        switch (sortType) {
            case "Sorted" -> {
                Arrays.sort(xs, 0, xs.length);
            }
            case "Reversed" -> {
                Arrays.sort(xs, Collections.reverseOrder());
            }
            default -> runBenchmarks(N, runs, xs);
        }

        helper.preProcess(xs);
        SortWithHelper<Integer> sorter = new ShellSort<>(h,helper);
        sorter.preProcess(xs);
        Integer[] ys = sorter.sort(xs);
        sorter.postProcess(ys);
        StatPack statPack = helper.getInstrumenter().getStatPack();
        int hits = (int) statPack.getStatistics(Instrumenter.HITS).mean();
        int compares = (int) statPack.getStatistics(Instrumenter.COMPARES).mean();
        logger.info("Hits for (" + sortType + "), N = " + N + ", h =  " + h + ": " + hits);
        logger.info("Compares for (" + sortType + "), N = " + N + ", h = " + h + ": " + compares);
    }

    private void runBenchmarks(int N, int runs, Integer[] xs) {
        System.out.println("============================================================");
        System.out.println("ShellSort Benchmark: N=" + N);
        String description = "Shell Sort";

        ShellSort<Integer> shellSort = new ShellSort<>(3);

        final double timeRandom = new Benchmark<Integer[]>(
                description + " (Random)",
                null,
                (x)->shellSort.sort(xs.clone(),0, xs.length),
                null
        ).run(xs, runs);
        for (TimeLogger timeLogger : timeLoggers) timeLogger.log(timeRandom, N);
    }

    private final static TimeLogger[] timeLoggers = {
            new TimeLogger("Raw time per run (mSec): ", (time, N) -> time)
    };
    
    public static void main(String[] args) throws IOException {
        int runs = 1000, N = 5000;

        // Shell sort for reverse sorted array
        while(N<=30000) {
            int h=1;
            while(h<=3280) {
                new ShellSortBenchMark(N, runs).getStats(h, "Reversed");
                h = 3 * h + 1;
            }
            N += 5000;
        }

        // Shell sort for sorted array
        N=5000;
        while(N<=30000) {
            int h=1;
            while(h<=3280) {
                new ShellSortBenchMark(N, runs).getStats(h, "Sorted");
                h = 3 * h + 1;
            }
            N += 5000;
        }

        // Shell sort for randomly sorted array
        N=5000;
        while(N<=30000) {
            int h=1;
            while(h<=3280) {
                new ShellSortBenchMark(N, runs).getStats(h, "Random");
                h = 3 * h + 1;
            }
            N += 5000;
        }
    }
    private final int runs;
    private final int N;
    final static LazyLogger logger = new LazyLogger(ShellSortBenchMark.class);
}
