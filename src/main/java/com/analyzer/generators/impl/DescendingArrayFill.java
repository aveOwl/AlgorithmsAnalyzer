package com.analyzer.generators.impl;

import com.analyzer.generators.Fill;
import com.analyzer.util.annotations.Filler;

/**
 * The {@code DescendingArrayFill} class populates an array
 * with items in descending order.
 */
public class DescendingArrayFill implements Fill {

    /**
     * Given number of elements generates an array of comparable
     * elements sorted in descending order.
     *
     * @param capacity number of elements in the array.
     * @return sorted array.
     */
    @Filler
    @Override
    public Comparable[] generate(Integer capacity) {
        Comparable[] array = new Comparable[capacity];
        Integer last = capacity;
        for (int i = 0; i < capacity; i++)
            array[i] = last--;
        return array;
    }
}
