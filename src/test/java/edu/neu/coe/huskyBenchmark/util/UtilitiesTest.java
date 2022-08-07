package edu.neu.coe.huskyBenchmark.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static edu.neu.coe.huskyBenchmark.util.Utilities.*;
import static org.junit.Assert.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

public class UtilitiesTest {
    @Test
    public void testLg() {
        assertEquals(0.0, lg(1), 1E-7);
        assertEquals(1.0, lg(2), 1E-7);
        assertEquals(0.5, lg(Math.sqrt(2)), 1E-7);
    }

    @Test
    public void testAsArray() {
        List<Double> array = new ArrayList<>();
        array.add(1.0);
        array.add(2.0);
        Double[] doubles = asArray(array);
        assertArrayEquals(new Double[]{1.0, 2.0}, doubles);
    }

    @Test
    public void testFormatDecimal3Places() {
        assertEquals("3.142", formatDecimal3Places(Math.PI));
    }

    @Test
    public void testFormatWhole() {
        assertEquals("42", formatWhole(42));
    }

    @Test
    public void testAsInt() {
        assertEquals("3", asInt(Math.PI));
    }

    @Test
    public void testRound() {
        assertEquals(3, round(Math.PI));
    }

    @Test
    public void testFillRandomArray() {
        Random r = new Random();
        Integer[] xs = fillRandomArray(Integer.class, r, 10, x->r.nextInt(100));
        Integer[] ys = fillRandomArray(Integer.class, r, 10, x->r.nextInt(100));
        assertThat(xs, not(equalTo(ys)));
    }

    @Test
    public void testSquareRoot() {
        assertEquals(10.0, sqRoot(100), 0);
        assertEquals(1.732, sqRoot(3.0), 0);
        assertEquals(1.414, sqRoot(2.0), 0);
    }
}
