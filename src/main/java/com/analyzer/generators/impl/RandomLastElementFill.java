package com.analyzer.generators.impl;

import com.analyzer.generators.Fill;
import com.analyzer.util.annotations.Filler;

import java.util.Random;

/**
 * The {@code RandomLastElementFill} class populates an array
 * with items in ascending order except one last element picked randomly.
 */
public class RandomLastElementFill implements Fill {

    /**
     * Given number of elements generates an array of comparable
     * elements in ascending order except one last element picked randomly.
     *
     * @param capacity number of elements in the array.
     * @return unsorted array.
     */
    @Filler
    @Override
    public Comparable[] generate(Integer capacity) {
        Comparable[] array = new Comparable[capacity];
        for (int i = 0; i < capacity - 1; )
            array[i] = ++i;
        int index = new Random().nextInt(capacity - 1);
        array[capacity - 1] = array[index];
        return array;
    }
}
