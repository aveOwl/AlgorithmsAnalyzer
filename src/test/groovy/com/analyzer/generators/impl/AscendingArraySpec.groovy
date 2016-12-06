package com.analyzer.generators.impl

import spock.lang.Specification

class AscendingArraySpec extends Specification {
    def gen = [] as AscendingArrayFill
    def capacity = 50

    def "should generate array in ascending order"() {
        given:
        def array = gen.generate(capacity)

        expect:
        array.size() == capacity
        (capacity - 1).times {
            assert array[it] < array[++it]
        }
    }
}
