package edu.neu.coe.huskyBenchmark.util;


import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Random;
import java.util.function.Function;

public final class Utilities {
    /**
     * There is really no better way that I could find to do this with library/language methods.
     * Don't try to inline this if the generic type extends something like Comparable, or you will get a ClassCastException.
     *
     * @param ts  a collection of Ts.
     * @param <T> the underlying type of ts.
     * @return an array T[].
     */
    public static <T> T[] asArray(final Collection<T> ts) {
        if (ts.isEmpty()) throw new RuntimeException("ts may not be empty");
        @SuppressWarnings("unchecked") final T[] result = (T[]) Array.newInstance(ts.iterator().next().getClass(), 0);
        return ts.toArray(result);
    }

    /**
     * Create a string representing a double, with three decimal places.
     *
     * @param x the number to show.
     * @return a String representing the number rounded to three decimal places.
     */
    public static String formatDecimal3Places(final double x) {
        final double scaleFactor = 1000.0;
        return String.format("%.3f", round(x * scaleFactor) / scaleFactor);
    }

    /**
     * Create a string representing an integer, with commas to separate thousands.
     *
     * @param x the integer.
     * @return a String representing the number with commas.
     */
    public static String formatWhole(final int x) {
        return String.format("%,d", x);
    }

    public static String asInt(final double x) {
        final int i = round(x);
        return formatWhole(i);
    }

    public static int round(final double x) {
        return (int) (Math.round(x));
    }

    public static <T> T[] fillRandomArray(final Class<T> clazz, final Random random, final int n, final Function<Random, T> f) {
        @SuppressWarnings("unchecked") final T[] result = (T[]) Array.newInstance(clazz, n);
        for (int i = 0; i < n; i++) result[i] = f.apply(random);
        return result;
    }

    /**
     * Return log to the base 2 of x.
     *
     * @param x the number whose log we require.
     * @return lg(x).
     */
    public static double lg(final double x) {
        return Math.log(x) / Math.log(2);
    }
}
