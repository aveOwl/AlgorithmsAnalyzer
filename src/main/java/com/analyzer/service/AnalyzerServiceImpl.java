package com.analyzer.service;

import com.analyzer.generators.Fill;
import com.analyzer.model.Record;
import com.analyzer.model.SortStatistics;
import com.analyzer.model.Statistics;
import com.analyzer.sorts.Sort;
import com.analyzer.util.ArraySpawnStrategy;
import com.analyzer.util.Stopwatch;
import com.analyzer.util.annotations.Filler;
import com.analyzer.util.annotations.Sorter;
import com.analyzer.util.exceptions.StatisticsEvaluationException;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code AnalyzerServiceImpl} is a basic implementation of {@link AnalyzerService} interface.
 * Utilizes the {@link Reflections} API to scan and retrieve all methods annotated with {@link Filler}
 * and {@link Sorter} annotations in order to compute {@link Statistics} object.
 */
public class AnalyzerServiceImpl implements AnalyzerService {
    private static final Logger LOG = LoggerFactory.getLogger(AnalyzerServiceImpl.class);
    private Reflections reflections;

    public AnalyzerServiceImpl() {
        this.reflections = new Reflections();
    }

    /**
     * <p>
     * Using {@link ArraySpawnStrategy} calculates {@link Statistics} object, which encapsulates data
     * describing sort method performance.
     * </p>
     *
     * @param strategy {@link ArraySpawnStrategy} to generate arrays with different capacities.
     * @return {@link Statistics} object.
     */
    @Override
    public Statistics generateStatistics(ArraySpawnStrategy strategy) {
        try {
            LOG.debug("Generating statistics by strategy: {}", strategy);
            return this.calculateStatistics(strategy);
        } catch (Exception e) {
            throw new StatisticsEvaluationException(strategy, e);
        }
    }

    /**
     * <p>
     * Searches for all classes implementing {@link Fill} interface, and methods in this classes
     * that are annotated with {@link Filler} annotation. Invokes each method and using provided
     * {@link ArraySpawnStrategy} populates the array of objects, on each method calculates {@link SortStatistics}.
     * </p>
     * <p>
     * For each invoked method writes new {@link Statistics} object, containing signature of the class
     * of type {@link Fill} and corresponding {@link SortStatistics} object.
     * </p>
     *
     * @param s strategy on how to populate the array.
     * @throws Exception on error.
     */
    private Statistics calculateStatistics(ArraySpawnStrategy s) throws Exception {
        List<Method> fillerMethods = this.getMethodsAnnotatedWith(Fill.class, Filler.class);
        List<Method> sortMethods = this.getMethodsAnnotatedWith(Sort.class, Sorter.class);
        LOG.debug("Retrieved {} methods annotated with Filler annotation", fillerMethods.size());
        LOG.debug("Retrieved {} methods annotated with Sorter annotation", sortMethods.size());

        Statistics statistics = new Statistics();
        for (Method fill : fillerMethods) {
            Object generator = fill.getDeclaringClass().newInstance();
            String fillerSignature = fill.getDeclaringClass().getSimpleName();

            LOG.debug("Generating SortStatistics for {}", fillerSignature);
            SortStatistics sortStatistics = new SortStatistics();
            for (int elements = s.minValue(); elements <= s.maxValue(); elements += s.step()) {
                Object[] array = (Object[]) fill.invoke(generator, elements);
                LOG.debug("Writing SortStatistics for {} elements", elements);
                this.writeSortStatistics(array, sortMethods, sortStatistics);
            }
            statistics.save(fillerSignature, sortStatistics);
            LOG.debug("For: {} saving statistics:\n{}", fillerSignature, sortStatistics);
        }
        return statistics;
    }

    /**
     * <p>
     * Searches for all classes implementing {@link Sort} interface, and methods in this classes
     * that are annotated with {@link Sorter} annotation. Invokes each method on the array of objects
     * and calculates elapsed CPU time to sort the array with the {@link Stopwatch} utility.
     * </p>
     * <p>
     * For each invoked method writes new {@link Record} to {@link SortStatistics} object,
     * which encapsulates data about elapsed time and number of elements in the array.
     * </p>
     *
     * @param array array of Objects to be sorted.
     * @throws Exception on error.
     */
    private void writeSortStatistics(Object[] array,
                                     List<Method> sortMethods,
                                     SortStatistics sortStatistics) throws Exception {
        for (Method sort : sortMethods) {
            Object sorter = sort.getDeclaringClass().newInstance();
            String sorterSignature = sort.getDeclaringClass().getSimpleName();
            LOG.debug("Sorting with: {}", sorterSignature);

            Stopwatch stopwatch = new Stopwatch();
            sort.invoke(sorter, new Object[]{array});
            Long elapsedTime = stopwatch.elapsedTime();

            Record record = new Record(elapsedTime, array.length);
            sortStatistics.save(sorterSignature, record);
        }
    }

    /**
     * Searches for all classes implementing specified interface and
     * returns list of methods that are annotated with specified annotation.
     *
     * @param anInterface specific interface.
     * @param annotation  specific method annotation.
     * @return list of methods.
     */
    private List<Method> getMethodsAnnotatedWith(Class<?> anInterface, Class<? extends Annotation> annotation) {
        LOG.debug("In classes of type: {} searching for methods annotated with: {} annotation",
                anInterface.getSimpleName(),
                annotation.getSimpleName());
        return this.reflections.getSubTypesOf(anInterface)
                .stream()
                .map(Class::getDeclaredMethods)
                .flatMap(Arrays::stream)
                .filter(method -> method.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }
}
