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
    public ShellSortBenchMark(int n, int runs) {
        this.n = n;
        this.runs = runs;
    }



    private void calculateCompares(int n,int h) throws IOException {
        final Config config = Config.load(getClass());
        ComparisonSortHelper<Integer> helper = HelperFactory.create("ShellSort", n, config);
        helper.init(n);

        Integer[] xs = helper.random(Integer.class, r -> r.nextInt(1000000));
        Arrays.sort(xs, Collections.reverseOrder());
        // Arrays.sort(xs,0,xs.length);
        // run timing benchmarking
        runBenchmarks(n, runs, xs);
        // finish timing benchmarking
        helper.preProcess(xs);
        SortWithHelper<Integer> sorter = new ShellSort<Integer>(h, helper);
        sorter.preProcess(xs);
        Integer[] ys = sorter.sort(xs);
        sorter.postProcess(ys);
        StatPack statPack = helper.getInstrumenter().getStatPack();
        int hits = (int) statPack.getStatistics(Instrumenter.HITS).mean();
        int compares = (int) statPack.getStatistics(Instrumenter.COMPARES).mean();
        System.out.println("Hits for random " + n + " Array Size: " + hits);
        System.out.println("Compares for random " + n + " Array Size: " + compares);

    }
        private void getStats(int n,int h,int value) throws IOException {
        final Config config = Config.load(getClass());
        ComparisonSortHelper<Integer> helper = HelperFactory.create("ShellSort", n, config);
        helper.init(n);
        String desc="";

        Integer[] xs = helper.random(Integer.class, r -> r.nextInt(1000000));

        switch (value)
            {
                case 1:
                    Arrays.sort(xs,0,xs.length);
                    desc="Sorted";
                    break;
                case 2:
                    Arrays.sort(xs, Collections.reverseOrder());
                    desc="Reversed";
                    break;
                 default :
                     desc="Random";
                    break;

            }
        // run timing benchmarking
        runBenchmarks(n, runs, xs);
        // finish timing benchmarking
        helper.preProcess(xs);
        SortWithHelper<Integer> sorter = new ShellSort<Integer>(h,helper);
        sorter.preProcess(xs);
        Integer[] ys = sorter.sort(xs);
        sorter.postProcess(ys);
        StatPack statPack = helper.getInstrumenter().getStatPack();
        int hits = (int) statPack.getStatistics(Instrumenter.HITS).mean();
        int compares = (int) statPack.getStatistics(Instrumenter.COMPARES).mean();
        System.out.println("Hits for "+desc+" "+ n +" Array Size and Gap is "+h+":"+hits);
        System.out.println("Compares for "+desc+" "+ n +" Array Size and Gap is "+h+":"+compares);


    }

    private void runBenchmarks(int n, int runs, Integer[] xs) {
        System.out.println("ShellSort Benchmark: N=" + n);
        String description = "Shell Sort";

        ShellSort<Integer> shellSort = new ShellSort<>(3);

        final double timeRandom = new Benchmark<Integer[]>(
                description + " (Random)",
                null,
                (x)->shellSort.sort(xs.clone(),0, xs.length),
                null
        ).run(xs, runs);
        for (TimeLogger timeLogger : timeLoggers) timeLogger.log(timeRandom, n);
    }

    private final static TimeLogger[] timeLoggers = {
            new TimeLogger("Raw time per run (mSec): ", (time, n) -> time)
    };


    public static void main(String[] args) throws IOException {
        int runs = 1000;
        for (int i=7; i<15; i++) {
            int n = (int) Math.pow(2, i);
            new ShellSortBenchMark(n, runs).getStats(n,3,3);
        }

        int n=5000;
        while(n<=30000)
        {
            int h=1;
            while(h<=3280) {
                new ShellSortBenchMark(n, runs).getStats(n, h, 3);
                h = 3 * h + 1;
            }
            n = n + 5000;
        }

    }
    private final int runs;
    private final int n;
    final static LazyLogger logger = new LazyLogger(ShellSortBenchMark.class);
}
