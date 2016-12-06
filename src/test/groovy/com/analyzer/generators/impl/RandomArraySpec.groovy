package com.analyzer.generators.impl

import spock.lang.Specification

class RandomArraySpec extends Specification {
    def gen = [] as RandomArrayFill
    def capacity = 50

    def "should generate array in random order"() {
        given:
        def array = gen.generate(capacity)

        expect:
        array.size() == capacity
        (capacity - 1).times {
            assert array[it] <= capacity
        }
    }
}
