package com.analyzer.model;

import com.analyzer.generators.Fill;
import com.analyzer.sorts.Sort;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * The {@code Statistics} data type describes overall performance of the specific sort
 * algorithm {@link Sort} depending on respective array generation strategy {@link Fill}.
 * </p>
 */
public class Statistics {
    private Map<String, SortStatistics> statistics;

    public Statistics() {
        this.statistics = new HashMap<>();
    }

    /**
     * Associates provided {@link SortStatistics} object with {@link Fill} generator.
     *
     * @param fillerSignature signature of the {@link Fill} generator.
     * @param sortStatistics  a single {@link SortStatistics} object to save.
     */
    public void save(String fillerSignature, SortStatistics sortStatistics) {
        this.statistics.put(fillerSignature, sortStatistics);
    }

    /**
     * Returns the {@link SortStatistics} object associated with given {@link Fill}
     * generator identified by its signature.
     *
     * @param signature signature of the {@link Fill} generator.
     * @return a {@link SortStatistics} object associated with specified {@link Fill} generator.
     */
    public SortStatistics getSortStatisticsByFillName(String signature) {
        return this.statistics.get(signature);
    }

    /**
     * Returns a {@link Set} of all {@link Fill} generator signatures
     * that this {@code Statistics} object contains.
     *
     * @return a {@link Set} of {@link Fill} generator signatures.
     */
    public Set<String> getFillNames() {
        return this.statistics.keySet();
    }

    /**
     * Returns a string representation of {@code Statistics} object.
     *
     * @return a string representation of {@code Statistics} object.
     */
    @Override
    public String toString() {
        String result = "";
        Set<String> fillerSignatures = this.statistics.keySet();
        for (String signature : fillerSignatures) {
            SortStatistics sortStatistics = this.statistics.get(signature);
            result += signature + "\n" + sortStatistics + "\n";
        }
        return result;
    }
}
