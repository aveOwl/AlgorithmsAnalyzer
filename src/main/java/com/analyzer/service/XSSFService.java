package com.analyzer.service;

import com.analyzer.model.Statistics;
import com.analyzer.util.ArraySpawnStrategy;

import java.nio.file.Path;

/**
 * <p>
 * The {@code XSSFService} service provides facilities to manipulate and store
 * data provided by {@link Statistics} object in form of <em>excel</em> file.
 * </p>
 */
public interface XSSFService {

    /**
     * Generates a single {@link Statistics} object using {@link AnalyzerService} service
     * and parses and process its enclosing data in form of <em>excel</em> file.
     *
     * @param strategy {@link ArraySpawnStrategy} object.
     * @param path     a path on the file system to save the completed file on.
     */
    void writeStatistics(ArraySpawnStrategy strategy, Path path);
}
