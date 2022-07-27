package edu.neu.coe.huskyBenchmark.sort.quick;

import edu.neu.coe.huskyBenchmark.sort.*;
import edu.neu.coe.huskyBenchmark.sort.simple.InsertionSort;
import edu.neu.coe.huskyBenchmark.util.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static edu.neu.coe.huskyBenchmark.util.Utilities.round;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("ALL")
public class QuickSortBasicTest {
    @Test
    public void testSort1() throws Exception {
        Integer[] xs = new Integer[4];
        xs[0] = 3;
        xs[1] = 4;
        xs[2] = 2;
        xs[3] = 1;
        GenericSort<Integer> s = new QuickSortBasic<>(Config.load(getClass()));
        Integer[] ys = s.sort(xs);
        assertEquals(Integer.valueOf(1), ys[0]);
        assertEquals(Integer.valueOf(2), ys[1]);
        assertEquals(Integer.valueOf(3), ys[2]);
        assertEquals(Integer.valueOf(4), ys[3]);
    }

    @Test
    public void testSort2() throws Exception {
        int n = 16;
        final SortWithHelper<Integer> sorter = new QuickSortBasic<>(Config.load(getClass()));
        final Helper<Integer> helper = sorter.getHelper();
        helper.init(n);
        final Integer[] xs = helper.random(Integer.class, r -> r.nextInt(100));
        final Integer[] sorted = sorter.sort(xs);
        assertTrue(helper.sorted(sorted));
    }

    @Test
    public void testPartition() throws Exception {
        String testString = "PABXWPPVPDPCYZ";
        char[] charArray = testString.toCharArray();
        Character[] array = new Character[charArray.length];
        for (int i = 0; i < array.length; i++) array[i] = charArray[i];
        GenericSort<Character> s = new QuickSortBasic<Character>(Config.load(getClass()));
        Partition<Character> p = ((QuickSort<Character>) s).createPartition(array, 0, array.length - 1);
        assertEquals(0, p.from);
        assertEquals(13, p.to);
        assertEquals(Character.valueOf('P'), array[0]);
        assertEquals(Character.valueOf('Z'), array[array.length - 1]);
    }


    @Test
    public void testSort() throws Exception {
        Integer[] xs = new Integer[4];
        xs[0] = 3;
        xs[1] = 4;
        xs[2] = 2;
        xs[3] = 1;
        GenericSort<Integer> s = new QuickSortBasic<>(xs.length, config);
        Integer[] ys = s.sort(xs);
        assertEquals(Integer.valueOf(1), ys[0]);
        assertEquals(Integer.valueOf(2), ys[1]);
        assertEquals(Integer.valueOf(3), ys[2]);
        assertEquals(Integer.valueOf(4), ys[3]);
    }

    @Test
    public void testSortWithInstrumenting0() throws Exception {
        int n = 16;
        final SortWithHelper<Integer> sorter = new QuickSortBasic<>(n, config);
        final Helper<Integer> helper = sorter.getHelper();
        final Integer[] xs = helper.random(Integer.class, r -> r.nextInt(10));
        final Integer[] sorted = sorter.sort(xs);
        assertTrue(helper.sorted(sorted));
    }

    @Test
    public void testSortWithInstrumenting1() throws Exception {
        int n = 541; // a prime number
        final SortWithHelper<Integer> sorter = new QuickSortBasic<>(n, config);
        final Helper<Integer> helper = sorter.getHelper();
        final Integer[] xs = helper.random(Integer.class, r -> r.nextInt(97));
        final Integer[] sorted = sorter.sort(xs);
        assertTrue(helper.sorted(sorted));
    }

    @Test
    public void testSortWithInstrumenting2() throws Exception {
        int n = 1000;
        final SortWithHelper<Integer> sorter = new QuickSortBasic<>(n, config);
        final Helper<Integer> helper = sorter.getHelper();
        final Integer[] xs = helper.random(Integer.class, r -> r.nextInt(100));
        final Integer[] sorted = sorter.sort(xs);
        assertTrue(helper.sorted(sorted));
    }

    @Test
    public void testSortWithInstrumenting3() throws Exception {
        int n = 1000;
        final SortWithHelper<Integer> sorter = new QuickSortBasic<>(n, config);
        final Helper<Integer> helper = sorter.getHelper();
        final Integer[] xs = helper.random(Integer.class, r -> r.nextInt(1000));
        final Integer[] sorted = sorter.sort(xs);
        assertTrue(helper.sorted(sorted));
    }

    @Test
    public void testSortWithInstrumenting4() throws Exception {
        int n = 1000;
        final SortWithHelper<Integer> sorter = new QuickSortBasic<>(n, config);
        final Helper<Integer> helper = sorter.getHelper();
        final Integer[] xs = helper.random(Integer.class, r -> r.nextInt(10000));
        final Integer[] sorted = sorter.sort(xs);
        assertTrue(helper.sorted(sorted));
    }

    @Test
    public void testSortWithInstrumenting5() throws Exception {
        int n = 10000;
        final SortWithHelper<Integer> sorter = new QuickSortBasic<>(n, config);
        final Helper<Integer> helper = sorter.getHelper();
        final Integer[] xs = helper.random(Integer.class, r -> r.nextInt(100000));
        final Integer[] sorted = sorter.sort(xs);
        assertTrue(helper.sorted(sorted));
    }

    @Test
    public void testStringSort() {
        String testString = "HBAXWPQVDCREZY";
        char[] charArray = testString.toCharArray();
        Character[] arr = new Character[charArray.length];
        for (int i = 0; i < arr.length; i++) arr[i] = charArray[i];
        GenericSort<Character> s = new QuickSortBasic<>(arr.length, config);
        Character[] res = s.sort(arr);
        char[] result = new char[res.length];
        for (int i = 0; i < arr.length; i++) result[i] = res[i];
        String resultString = new String(result);
        assertEquals("ABCDEHPQRVWXYZ", resultString);
    }

    @Test
    public void testPartition1() throws Exception {
        String testString = "HBAXWPQVDCREZY";
        char[] charArray = testString.toCharArray();
        Character[] array = new Character[charArray.length];
        for (int i = 0; i < array.length; i++) array[i] = charArray[i];
        final Config config = ConfigTest.setupConfig("true", "0", "1", "", "");
        QuickSort<Character> sorter = new QuickSortBasic<Character>(array.length, config);
        Partitioner<Character> partitioner = sorter.partitioner;
        List<Partition<Character>> partitions = partitioner.partition(QuickSort.createPartition(array));
        assertEquals(2, partitions.size());
        Partition<Character> p0 = partitions.get(0);
        assertEquals(0, p0.from);
        assertEquals(5, p0.to);
        Partition<Character> p1 = partitions.get(1);
        assertEquals(6, p1.from);
        assertEquals(14, p1.to);
        char[] chars = new char[array.length];
        for (int i = 0; i < chars.length; i++) chars[i] = array[i];
        String partitionedString = new String(chars);
        assertEquals("DBAECHQVPWRXZY", partitionedString);
    }

    @Test
    public void testPartition2() throws Exception {
        String testString = "SEAYRLFVZQTCMK";
        char[] charArray = testString.toCharArray();
        Character[] array = new Character[charArray.length];
        for (int i = 0; i < array.length; i++) array[i] = charArray[i];
        final Config config = ConfigTest.setupConfig("true", "0", "1", "", "");
        QuickSort<Character> sorter = new QuickSortBasic<Character>(array.length, config);
        Partitioner<Character> partitioner = sorter.partitioner;
        List<Partition<Character>> partitions = partitioner.partition(QuickSort.createPartition(array));
        assertEquals(2, partitions.size());
        Partition<Character> p0 = partitions.get(0);
        assertEquals(0, p0.from);
        assertEquals(9, p0.to);
        Partition<Character> p1 = partitions.get(1);
        assertEquals(10, p1.from);
        assertEquals(14, p1.to);
        char[] chars = new char[array.length];
        for (int i = 0; i < chars.length; i++) chars[i] = array[i];
        String partitionedString = new String(chars);
        assertEquals("QEAKRLFMCSTZVY", partitionedString);
    }

    @Test
    public void testRandom() {
        int N = 5;
        Shuffle<Integer> sh = new Shuffle<>(N);
        Integer[] xs = sh.supply();
        final Config config = ConfigTest.setupConfig("true", "0", "1", "1", "");
        ComparisonSortHelper<Integer> helper = HelperFactory.create("QuickSort basic", N, true, config);
        helper.init(N);

        final PrivateMethodInvoker privateMethodInvoker = new PrivateMethodInvoker(helper);
        final StatPack statPack = (StatPack) privateMethodInvoker.invokePrivate("getStatPack");
//        Integer[] xs = helper.random(Integer.class, r -> r.nextInt(10));
        System.out.println(Arrays.toString(xs));
        SortWithHelper<Integer> sorter = new QuickSortBasic<>(helper);
        sorter.preProcess(xs);
        Integer[] ys = sorter.sort(xs);
        sorter.postProcess(ys);
        assertTrue(helper.sorted(ys));
        final int compares = (int) statPack.getStatistics(Instrumenter.COMPARES).mean();
        final int swaps = (int) statPack.getStatistics(Instrumenter.SWAPS).mean();
        System.out.println(statPack);
        System.out.println(compares*1.0/swaps);
    }

    @Test
    public void testSortDetailed() throws Exception {
        int k = 7;
        int N = 5;
        // NOTE this depends on the cutoff value for quick sort.
        int levels = k - 2;
        final Config config = ConfigTest.setupConfig("true", "0", "1", "", "");
        final BaseHelper<Integer> helper = (BaseHelper<Integer>) HelperFactory.create("QuickSort basic", N, config);
        System.out.println(helper);
        Sort<Integer> s = new QuickSortBasic<Integer>((ComparisonSortHelper<Integer>) helper);
        s.init(N);
        final Integer[] xs = helper.random(Integer.class, r -> r.nextInt(10000));
//        assertEquals(Integer.valueOf(1360), xs[0]);
        helper.preProcess(xs);
        Integer[] ys = s.sort(xs);
        helper.postProcess(ys);
        final PrivateMethodInvoker privateMethodTester = new PrivateMethodInvoker(helper);
        final StatPack statPack = (StatPack) privateMethodTester.invokePrivate("getStatPack");
        System.out.println(statPack);
        final int compares = (int) statPack.getStatistics(Instrumenter.COMPARES).mean();
        final int swaps = (int) statPack.getStatistics(Instrumenter.SWAPS).mean();
        System.out.println("ratio of compares to swaps: " + compares*1.0/swaps);
    }

    @Test
    public void testSortDetailedRandom() throws Exception {
        int k = 14;
        int N = (int) Math.pow(2, k);
        // NOTE this depends on the cutoff value for quick sort.
        int levels = k - 2;
        final Config config = ConfigTest.setupConfig("true", "", "1", "1", "");
        final BaseHelper<Integer> helper = (BaseHelper<Integer>) HelperFactory.create("QuickSort basic", N, config);
        System.out.println(helper);
        Sort<Integer> s = new QuickSortBasic<Integer>((ComparisonSortHelper<Integer>) helper);
        s.init(N);
        final Integer[] xs = helper.random(Integer.class, r -> r.nextInt(10000));
        helper.preProcess(xs);
        Integer[] ys = s.sort(xs);
        assertTrue(helper.sorted(ys));
        helper.postProcess(ys);
        final PrivateMethodInvoker privateMethodTester = new PrivateMethodInvoker(helper);
        final StatPack statPack = (StatPack) privateMethodTester.invokePrivate("getStatPack");
        System.out.println(statPack);
        final int compares = (int) statPack.getStatistics(Instrumenter.COMPARES).mean();
        final int inversions = (int) statPack.getStatistics(Instrumenter.INVERSIONS).mean();
        final int fixes = (int) statPack.getStatistics(Instrumenter.FIXES).mean();
        final int swaps = (int) statPack.getStatistics(Instrumenter.SWAPS).mean();
        final int copies = (int) statPack.getStatistics(Instrumenter.COPIES).mean();
        final int worstCompares = round(2.0 * (N+1) * Math.log(N));
        final int bestCompares = round(N * k);
        System.out.println("bestCompares: " + bestCompares + ", compares: " + compares + ", worstCompares: " + worstCompares);
//        assertTrue(compares <= worstCompares);
        System.out.println("ratio of compares to swaps: " + worstCompares*1.0/swaps);
    }

    @Test
    public void runBenchmarks() {
        int sum = 0;
        for( int N=7; N<=16; N++) {
//            int N = (int) Math.pow(2, i);
            final Config config = ConfigTest.setupConfig("true", "", "1", "1", "");
            final BaseHelper<Integer> helper = (BaseHelper<Integer>) HelperFactory.create("QuickSort basic", N, config);
//            System.out.println(helper);
            Sort<Integer> s = new QuickSortBasic<Integer>((ComparisonSortHelper<Integer>) helper);
            s.init(N);
            final Integer[] xs = helper.random(Integer.class, r -> r.nextInt(1000000));
            helper.preProcess(xs);
            Integer[] ys = s.sort(xs);
            assertTrue(helper.sorted(ys));
            helper.postProcess(ys);
            final PrivateMethodInvoker privateMethodTester = new PrivateMethodInvoker(helper);
            final StatPack statPack = (StatPack) privateMethodTester.invokePrivate("getStatPack");
            final int compares = (int) statPack.getStatistics(Instrumenter.COMPARES).mean();
            final int inversions = (int) statPack.getStatistics(Instrumenter.INVERSIONS).mean();
            final int fixes = (int) statPack.getStatistics(Instrumenter.FIXES).mean();
            final int swaps = (int) statPack.getStatistics(Instrumenter.SWAPS).mean();
            final int copies = (int) statPack.getStatistics(Instrumenter.COPIES).mean();
            final int worstCompares = round(2.0 * (N+1) * Math.log(N));
//            final int bestCompares = round(N * i);
            System.out.println("avgCompares: " + compares + ", swaps: " + swaps);
            assertTrue(compares <= worstCompares);
            System.out.println("ratio of compares to swaps: " + worstCompares*1.0/swaps);
            sum += worstCompares*1.0/swaps;
        }
        System.out.println("avg: " + sum/10.0);
    }

    final static LazyLogger logger = new LazyLogger(QuickSortBasic.class);

    @BeforeClass
    public static void beforeClass() throws IOException {
        config = Config.load();
    }

    private static Config config;
}