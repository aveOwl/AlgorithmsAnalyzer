package com.analyzer.generators.impl

import spock.lang.Specification

class LastElementRandomArraySpec extends Specification {
    def gen = [] as RandomLastElementFill
    def capacity = 50

    def "should generate array in ascending order with last element random"() {
        given:
        def array = gen.generate(capacity)

        expect:
        array.size() == capacity
        (capacity - 2).times {
            assert array[it] < array[++it]
        }
        array[capacity - 1] <= capacity
    }
}
