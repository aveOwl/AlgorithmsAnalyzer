package com.analyzer.util.exceptions;

import com.analyzer.util.ArraySpawnStrategy;

public class StatisticsEvaluationException extends RuntimeException {

    public StatisticsEvaluationException(ArraySpawnStrategy strategy, Exception e) {
        super("Failed to calculate statistics with strategy " + strategy, e);
    }
}
