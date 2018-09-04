package com.analyzer.generators.impl;

import com.analyzer.generators.Fill;
import com.analyzer.util.annotations.Filler;

/**
 * The {@code AscendingArrayFill} class populates an array
 * with items in ascending order.
 */
public class AscendingArrayFill implements Fill {

    /**
     * Given number of elements generates an array of comparable
     * elements sorted in ascending order.
     *
     * @param capacity number of elements in the array.
     * @return sorted array.
     */
    @Filler
    @Override
    public Comparable[] generate(Integer capacity) {
        Comparable[] array = new Comparable[capacity];
        for (int i = 0; i < capacity; )
            array[i] = ++i;
        return array;
    }
}
