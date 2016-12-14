package com.analyzer.util;

/**
 * The {@code Stopwatch} data type is used to measure the CPU time that
 * elapses between the start and end of a programming task.
 */
public class Stopwatch {
    private final Long start;

    /**
     * Initialize a new stopwatch.
     */
    public Stopwatch() {
        this.start = System.nanoTime();
    }

    /**
     * Returns the elapsed time (in nanoseconds) since the stopwatch was created.
     *
     * @return the elapsed time (in nanoseconds) since the stopwatch was created.
     */
    public Long elapsedTime() {
        Long now = System.nanoTime();
        return now - start;
    }
}
