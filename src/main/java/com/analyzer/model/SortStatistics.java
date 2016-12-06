package com.analyzer.model;

import com.analyzer.sorts.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * The {@code SortStatistics} data type describes overall performance of the specific sort
 * algorithm {@link Sort}, generating a list of {@link Record} objects.
 * </p>
 */
public class SortStatistics {
    private Map<String, List<Record>> sortStatistics;

    public SortStatistics() {
        this.sortStatistics = new HashMap<>();
    }

    /**
     * Associates provided {@link Record} object with {@link Sort} algorithm.
     *
     * @param sortSignature signature of the {@link Sort} algorithm.
     * @param record        a single {@link Record} to save.
     */
    public void save(String sortSignature, Record record) {
        List<Record> records;
        if (this.isSortNew(sortSignature)) {
            records = this.sortStatistics.get(sortSignature);
        } else {
            records = new ArrayList<>();
            this.sortStatistics.put(sortSignature, records);
        }
        records.add(record);
    }

    /**
     * Returns all the {@link Record} records associated with given {@link Sort}
     * algorithm identified by its signature.
     *
     * @param signature signature of the {@link Sort} algorithm.
     * @return a list of records for given {@link Sort} algorithm.
     */
    public List<Record> getRecordsBySortName(String signature) {
        return this.sortStatistics.get(signature);
    }

    /**
     * Returns a {@link Set} of all {@link Sort} algorithms signatures
     * that this {@code SortStatistics} object contains.
     *
     * @return a {@link Set} of {@link Sort} algorithms signatures.
     */
    public Set<String> getSortNames() {
        return this.sortStatistics.keySet();
    }

    /**
     * Checks whether {@link Sort} algorithm already present in {@code SortStatustucs}
     *
     * @param sortSignature signature of the {@link Sort} algorithm.
     * @return true if sort algorithm is present, false otherwise.
     */
    private boolean isSortNew(String sortSignature) {
        return this.sortStatistics.containsKey(sortSignature);
    }

    /**
     * Returns a string representation of {@code SortStatistics} object.
     *
     * @return a string representation of {@code SortStatistics} object.
     */
    @Override
    public String toString() {
        String result = "";
        Set<String> sortSignatures = this.sortStatistics.keySet();
        for (String signature : sortSignatures) {
            List<Record> records = this.sortStatistics.get(signature);
            result += signature + " : " + records + "\n";
        }
        return result;
    }
}
