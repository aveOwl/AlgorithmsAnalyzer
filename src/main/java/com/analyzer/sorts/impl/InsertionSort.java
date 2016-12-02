package com.analyzer.sorts.impl;

import com.analyzer.sorts.Sort;
import com.analyzer.util.annotations.Sorter;

public class InsertionSort implements Sort {

    @Sorter
    @Override
    public Comparable[] sort(Comparable[] a) {
        if (a.length < 2) return a;

        for (int i = 0; i < a.length; i++) {
            for (int j = i; j > 0; j--) {
                if (this.less(a[j], a[j - 1])) {
                    this.exch(a, j, j - 1);
                } else {
                    break;
                }
            }
        }
        return a;
    }
}
