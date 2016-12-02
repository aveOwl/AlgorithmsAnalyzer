package com.analyzer.model;

import com.analyzer.sorts.Sort;

/**
 * <p>
 * The {@code Record} data type describes a single unit of {@link Sort} algorithm performance
 * containing information about number of elements in the array and CPU time taken to sort the array.
 * </p>
 */
public class Record {
    private Long elapsedTime;
    private Integer numberOfElements;

    public Record(Long elapsedTime, Integer numberOfElements) {
        this.elapsedTime = elapsedTime;
        this.numberOfElements = numberOfElements;
    }

    /**
     * Returns time taken to sort the array.
     *
     * @return time taken to sort the array.
     */
    public Long getElapsedTime() {
        return this.elapsedTime;
    }

    /**
     * Returns number of elements in the array.
     *
     * @return number of elements in the array.
     */
    public Integer getNumberOfElements() {
        return this.numberOfElements;
    }

    /**
     * Returns a string representation of {@code Record} object.
     *
     * @return a string representation of {@code Record} object.
     */
    @Override
    public String toString() {
        return "Record{" + "elapsedTime=" + this.elapsedTime + ", numberOfElements=" + this.numberOfElements + '}';
    }
}
