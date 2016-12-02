package com.analyzer.sorts.impl;

import com.analyzer.sorts.Sort;
import com.analyzer.util.annotations.Sorter;

public class BubbleSort implements Sort {

    @Sorter
    @Override
    public Comparable[] sort(Comparable[] a) {
        if (a.length < 2) return a;

        for (int i = a.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (this.less(a[j + 1], a[j])) {
                    this.exch(a, j, j + 1);
                }
            }
        }
        return a;
    }
}
