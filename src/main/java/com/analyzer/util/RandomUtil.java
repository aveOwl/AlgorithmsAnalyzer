package com.analyzer.util;

import java.util.Random;

/**
 * <p>
 * The {@code RandomUtil} class provides methods to generate random
 * number and random sequences of numbers and methods for shuffling the
 * arrays.
 * </p>
 */
public class RandomUtil {

    /**
     * Rearranges the elements of the specified array in uniformly manner.
     *
     * @param a the array to shuffle.
     */
    public static void shuffle(Comparable[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + new Random().nextInt(n - i);
            Comparable temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
}
