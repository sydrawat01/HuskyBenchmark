package edu.neu.coe.huskyBenchmark.util;

/**
 * This interface defines the additional behaviors of Instrumented Helpers.
 */
public interface Instrumented {
    /**
     * Get the statistics pack for this Instrumented Helper.
     *
     * @return a StatPack.
     */
    StatPack getStatPack();
}
