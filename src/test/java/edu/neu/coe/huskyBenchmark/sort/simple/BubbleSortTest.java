package edu.neu.coe.huskyBenchmark.sort.simple;

import edu.neu.coe.huskyBenchmark.sort.*;
import edu.neu.coe.huskyBenchmark.util.*;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BubbleSortTest {
    @Test
    public void sort0() throws Exception {
        final List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Integer[] xs = list.toArray(new Integer[0]);
        final Config config = ConfigTest.setupConfig("true", "0", "1", "", "");
        ComparisonSortHelper<Integer> helper = HelperFactory.create("BubbleSort", list.size(), config);
        helper.init(list.size());
        final PrivateMethodInvoker privateMethodTester = new PrivateMethodInvoker(helper);
        final StatPack statPack = (StatPack) privateMethodTester.invokePrivate("getStatPack");
        SortWithHelper<Integer> sorter = new BubbleSort<Integer>(helper);
        sorter.preProcess(xs);
        Integer[] ys = sorter.sort(xs);
        sorter.postProcess(ys);
        assertTrue(helper.sorted(ys));
        final int compares = (int) statPack.getStatistics(Instrumenter.COMPARES).mean();
        assertEquals(list.size() - 1, compares);
        final int inversions = (int) statPack.getStatistics(Instrumenter.INVERSIONS).mean();
        assertEquals(0L, inversions);
        final int fixes = (int) statPack.getStatistics(Instrumenter.FIXES).mean();
        assertEquals(inversions, fixes);
    }

    @Test
    public void sort1() throws Exception {
        final List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(2);
        list.add(1);
        Integer[] xs = list.toArray(new Integer[0]);
        ComparableSortHelper<Integer> helper = new ComparableSortHelper<Integer>("BubbleSort", xs.length);
        GenericSort<Integer> sorter = new BubbleSort<Integer>(helper);
        Integer[] ys = sorter.sort(xs);
        assertTrue(helper.sorted(ys));
        System.out.println(sorter.toString());
    }

    @Test
    public void testMutatingBubbleSort() throws IOException {
        final List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(2);
        list.add(1);
        Integer[] xs = list.toArray(new Integer[0]);
        ComparableSortHelper<Integer> helper = new ComparableSortHelper<Integer>("BubbleSort", xs.length);
        GenericSort<Integer> sorter = new BubbleSort<>(helper);
        sorter.mutatingSort(xs);
        assertTrue(helper.sorted(xs));
    }

    @Test
    public void sort2() throws Exception {
        final Config config = ConfigTest.setupConfig("true", "0", "1", "", "");
        int n = 100;
        ComparisonSortHelper<Integer> helper = HelperFactory.create("BubbleSort", n, config);
        helper.init(n);
        final PrivateMethodInvoker privateMethodTester = new PrivateMethodInvoker(helper);
        final StatPack statPack = (StatPack) privateMethodTester.invokePrivate("getStatPack");
        Integer[] xs = helper.random(Integer.class, r -> r.nextInt(1000));
        SortWithHelper<Integer> sorter = new BubbleSort<Integer>(helper);
        sorter.preProcess(xs);
        Integer[] ys = sorter.sort(xs);
        sorter.postProcess(ys);
        assertTrue(helper.sorted(ys));
        final int compares = (int) statPack.getStatistics(Instrumenter.COMPARES).mean();
        // NOTE: these are supposed to match within about 12%.
        // Since we set a specific seed, this should always succeed.
        // If we use true random seed and this test fails, just increase the delta a little.
        assertEquals(1.0, 2.0 * compares / n / (n - 1), 0.12);
        final int inversions = (int) statPack.getStatistics(Instrumenter.INVERSIONS).mean();
        final int fixes = (int) statPack.getStatistics(Instrumenter.FIXES).mean();
        System.out.println(statPack);
        assertEquals(inversions, fixes);
    }

    final static LazyLogger logger = new LazyLogger(BubbleSort.class);
}
