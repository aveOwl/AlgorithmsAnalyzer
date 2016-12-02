package com.analyzer.sorts.impl;

import com.analyzer.sorts.Sort;
import com.analyzer.util.annotations.Sorter;

public class MergeSort implements Sort {

    @Sorter
    @Override
    public Comparable[] sort(Comparable[] a) {
        if (a.length < 2) return a;

        Comparable[] helper = new Comparable[a.length];
        this.mergeSort(a, helper, 0, a.length - 1);
        return a;
    }

    private void mergeSort(Comparable[] a, Comparable[] helper, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;

        this.mergeSort(a, helper, lo, mid);
        this.mergeSort(a, helper, mid + 1, hi);
        this.merge(a, helper, lo, mid, hi);
    }

    private void merge(Comparable[] a, Comparable[] helper, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) {
            helper[k] = a[k];
        }

        int i = lo;
        int j = mid + 1;

        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = helper[j++];
            } else if (j > hi) {
                a[k] = helper[i++];
            } else if (this.less(helper[j], helper[i])) {
                a[k] = helper[j++];
            } else {
                a[k] = helper[i++];
            }
        }
    }
}
