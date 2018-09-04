package com.analyzer.generators.impl;

import com.analyzer.generators.Fill;
import com.analyzer.util.RandomUtil;
import com.analyzer.util.annotations.Filler;

/**
 * The {@code RandomArrayFill} class populates an array
 * with items in random order.
 */
public class RandomArrayFill implements Fill {

    /**
     * Given number of elements generates an array of comparable
     * elements in random order.
     *
     * @param capacity number of elements in the array.
     * @return unsorted array.
     */
    @Filler
    @Override
    public Comparable[] generate(Integer capacity) {
        Comparable[] array = new Comparable[capacity];
        for (int i = 0; i < capacity; )
            array[i] = ++i;
        RandomUtil.shuffle(array);
        return array;
    }
}
