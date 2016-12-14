package com.analyzer.generators;

/**
 * Generates an array of {@link Comparable} elements using different strategies.
 */
@FunctionalInterface
public interface Fill {

    /**
     * Generates array of given capacity.
     *
     * @param capacity number of elements in the array.
     * @return an array.
     */
    Comparable[] generate(Integer capacity);
}
