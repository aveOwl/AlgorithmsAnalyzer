package com.analyzer.sorts.impl;

import com.analyzer.sorts.Sort;
import com.analyzer.util.annotations.Sorter;

public class SelectionSort implements Sort {

    @Sorter
    @Override
    public Comparable[] sort(Comparable[] a) {
        if (a.length < 2) return a;

        for (int i = 0; i < a.length; i++) {
            int min = i;

            for (int j = i + 1; j < a.length; j++) {
                if (this.less(a[j], a[min])) {
                    min = j;
                }
            }
            this.exch(a, i, min);
        }
        return a;
    }
}
