package com.analyzer.service;

import com.analyzer.model.Statistics;
import com.analyzer.sorts.Sort;
import com.analyzer.util.ArraySpawnStrategy;

/**
 * <p>
 * The {@code AnalyzerService} service provides facilities to generate a single {@link Statistics} object
 * describing performance of various {@link Sort} algorithms providing the {@link ArraySpawnStrategy}
 * to generate the arrays of different capacities.
 * </p>
 */
@FunctionalInterface
public interface AnalyzerService {

    /**
     * Generates a single {@link Statistics} object, calculated
     * using provided {@link ArraySpawnStrategy} strategy.
     *
     * @param strategy strategy to generate arrays of different capacities.
     * @return a {@link Statistics} object, describing {@link Sort} algorithms performance.
     */
    Statistics generateStatistics(ArraySpawnStrategy strategy);
}
