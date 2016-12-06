package com.analyzer.service

import com.analyzer.model.Statistics
import com.analyzer.util.ArraySpawnStrategy
import spock.lang.Specification

class AnalyzerServiceSpec extends Specification {
    def analyzer = [] as AnalyzerServiceImpl
    def statistics = [] as Statistics

    def "should generate statistics for specified strategy"() {
        given:
        def strategy = [5, 10, 5] as ArraySpawnStrategy

        when:
        statistics = analyzer.generateStatistics(strategy)

        then:
        statistics.getSortStatisticsByFillName("AscendingArrayFill")
                .getRecordsBySortName("BubbleSort")
                .size() == 2
    }
}

