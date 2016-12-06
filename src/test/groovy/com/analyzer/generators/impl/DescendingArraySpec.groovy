package com.analyzer.generators.impl

import spock.lang.Specification

class DescendingArraySpec extends Specification {
    def gen = [] as DescendingArrayFill
    def capacity = 50

    def "should generate array in descending order"() {
        given:
        def array = gen.generate(capacity)

        expect:
        array.size() == capacity
        (capacity - 1).times {
            assert array[it] > array[++it]
        }
    }
}
