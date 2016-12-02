package com.analyzer.generators.impl;

import com.analyzer.generators.Fill;
import com.analyzer.util.annotations.Filler;

import java.util.Random;

public class RandomLastElementFill implements Fill {

    @Filler
    @Override
    public Comparable[] generate(Integer capacity) {
        Comparable[] array = new Comparable[capacity];
        for (int i = 0; i < capacity - 1; )
            array[i] = ++ i;
        int index = new Random().nextInt(capacity - 1);
        array[capacity - 1] = array[index];
        return array;
    }
}
