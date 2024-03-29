package edu.neu.coe.huskyBenchmark.sort;

import java.util.Arrays;

public class SortException extends RuntimeException {
    public SortException(final String message) {
        super(message);
    }

    public SortException(final String message, final Object x) {
        super(message + ": " + (x.getClass().isArray() ? Arrays.toString((Object[]) x) : x.toString()));
    }

    public SortException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public SortException(final Throwable cause) {
        super(cause);
    }

    public SortException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
