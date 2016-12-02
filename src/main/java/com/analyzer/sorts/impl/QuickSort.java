package com.analyzer.sorts.impl;

import com.analyzer.sorts.Sort;
import com.analyzer.util.RandomUtil;
import com.analyzer.util.annotations.Sorter;

public class QuickSort implements Sort {

    @Sorter
    @Override
    public Comparable[] sort(Comparable[] a) {
        if (a.length < 2) return a;

        RandomUtil.shuffle(a);
        this.quickSort(a, 0, a.length - 1);
        return a;
    }

    private void quickSort(Comparable[] a, int left, int right) {
        if (right <= left) return;
        int index = this.partition(a, left, right);

        this.quickSort(a, left, index - 1);
        this.quickSort(a, index + 1, right);
    }

    private int partition(Comparable[] a, int left, int right) {
        int i = left;
        int j = right + 1;

        while (true) {
            // find item on the left to swap
            while (this.less(a[++ i], a[left])) {
                if (i == right) break;
            }
            // find item on the right to swap
            while (this.less(a[left], a[-- j])) {
                if (j == left) break;
            }

            // check pointers cross
            if (i >= j) break;
            this.exch(a, i, j);
        }

        this.exch(a, left, j);
        // now, a[left .. j-1] <= a[j] <= a[j+1 .. right]
        return j;
    }
}