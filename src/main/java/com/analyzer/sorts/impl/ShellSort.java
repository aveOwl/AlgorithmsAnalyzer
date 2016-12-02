package com.analyzer.sorts.impl;

import com.analyzer.sorts.Sort;
import com.analyzer.util.annotations.Sorter;

public class ShellSort implements Sort {

    @Sorter
    @Override
    public Comparable[] sort(Comparable[] a) {
        if (a.length < 2) return a;

        int size = a.length;

        int h = 1;
        while (h < size / 3) {
            h = h * 3 + 1;
        }

        while (h >= 1) {
            for (int i = h; i < size; i++) {
                for (int j = i; j >= h && this.less(a[j], a[j - h]); j -= h)
                    this.exch(a, j, j - h);
            }
            h = h / 3;
        }
        return a;
    }
}
