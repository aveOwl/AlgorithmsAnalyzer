package com.analyzer.sorts;

/**
 * The {@code Sort} interface is a functional interface
 * with single method to sort the array of {@link Comparable} items.
 */
@FunctionalInterface
public interface Sort {

    /**
     * Sorts the array of {@link Comparable} elements in ascending order.
     *
     * @param a an array to sort.
     * @return sorted array.
     */
    Comparable[] sort(Comparable[] a);

    /**
     * Swaps to elements in the array.
     *
     * @param array array in which to swap the elements.
     * @param left  index of the element to swap.
     * @param right index of the element to swap.
     */
    default void exch(Comparable[] array, int left, int right) {
        Comparable temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }

    /**
     * Compares two {@link Comparable} elements.
     *
     * @param v an element in the array.
     * @param w an element in the array.
     * @return true if second element is greater than the first, false otherwise.
     */
    default boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
}
