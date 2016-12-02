package com.analyzer.generators.impl;

import com.analyzer.generators.Fill;
import com.analyzer.util.RandomUtil;
import com.analyzer.util.annotations.Filler;

public class RandomArrayFill implements Fill {

    @Filler
    @Override
    public Comparable[] generate(Integer capacity) {
        Comparable[] array = new Comparable[capacity];
        for (int i = 0; i < capacity; )
            array[i] = ++ i;
        RandomUtil.shuffle(array);
        return array;
    }
}
