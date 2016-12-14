package com.analyzer.sorts

import com.analyzer.generators.impl.AscendingArrayFill
import com.analyzer.generators.impl.DescendingArrayFill
import com.analyzer.generators.impl.RandomLastElementFill
import com.analyzer.generators.impl.RandomArrayFill
import com.analyzer.sorts.impl.*
import spock.lang.Specification

class SortSpec extends Specification {
    def capacity = 500
    def sorts = [[] as BubbleSort, [] as SelectionSort, [] as InsertionSort,
                 [] as MergeSort, [] as QuickSort, [] as ShellSort]

    def "should sort sorted array"() {
        given:
        def gen = [] as AscendingArrayFill
        def array = gen.generate(capacity)

        when:
        sorts.each {
            array = it.sort(array)
        }

        then:
        array.size() == capacity
        verifyOrder(array)
    }

    def "should sort descending array"() {
        given:
        def gen = [] as DescendingArrayFill
        def array = gen.generate(capacity)

        when:
        sorts.each {
            array = it.sort(array)
        }

        then:
        array.size() == capacity
        verifyOrder(array)
    }

    def "should sort last element random"() {
        given:
        def gen = [] as RandomLastElementFill
        def array = gen.generate(capacity)

        when:
        sorts.each {
            array = it.sort(array)
        }

        then:
        array.size() == capacity
        verifyOrder(array)
    }

    def "should sort random array"() {
        given:
        def gen = [] as RandomArrayFill
        def array = gen.generate(capacity)

        when:
        sorts.each {
            array = it.sort(array)
        }

        then:
        array.size() == capacity
        verifyOrder(array)
    }

    private void verifyOrder(Comparable[] array) {
        (capacity - 1).times {
            array[it] < array[++it]
        }
    }
}
