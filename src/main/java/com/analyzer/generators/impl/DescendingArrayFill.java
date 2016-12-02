package com.analyzer.generators.impl;

import com.analyzer.generators.Fill;
import com.analyzer.util.annotations.Filler;

public class DescendingArrayFill implements Fill {

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
