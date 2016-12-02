package com.analyzer.generators;

/**
 * <p>
 * Generates an array of {@link Comparable} elements using different strategies.
 * </p>
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
