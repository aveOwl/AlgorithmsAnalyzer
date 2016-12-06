package com.analyzer.service;

import com.analyzer.generators.Fill;
import com.analyzer.model.Record;
import com.analyzer.model.SortStatistics;
import com.analyzer.model.Statistics;
import com.analyzer.sorts.Sort;
import com.analyzer.util.ArraySpawnStrategy;
import com.analyzer.util.Stopwatch;
import com.analyzer.util.exceptions.StatisticStorageException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.charts.AxisCrosses;
import org.apache.poi.ss.usermodel.charts.AxisPosition;
import org.apache.poi.ss.usermodel.charts.ChartAxis;
import org.apache.poi.ss.usermodel.charts.ChartDataSource;
import org.apache.poi.ss.usermodel.charts.DataSources;
import org.apache.poi.ss.usermodel.charts.LegendPosition;
import org.apache.poi.ss.usermodel.charts.LineChartData;
import org.apache.poi.ss.usermodel.charts.ScatterChartData;
import org.apache.poi.ss.usermodel.charts.ScatterChartSeries;
import org.apache.poi.ss.usermodel.charts.ValueAxis;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.charts.XSSFChartLegend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static java.nio.file.StandardOpenOption.CREATE;

/**
 * <p>
 * The {@code XSSFServiceImpl} is a basic implementation of the {@link XSSFService} interface.
 * </p>
 * <p>
 * Completed file consists of the table containing statistics on each {@link Sort}
 * sort method, its signature and corresponding {@link Record} records with number
 * of elements in array and CPU time taken to sort the array, computed with {@link Stopwatch}
 * clock. Also file contains line chart representing the relationship between the running time
 * of the sort and the number of elements in the array.
 * </p>
 */
public class XSSFServiceImpl implements XSSFService {
    private static final Logger LOG = LoggerFactory.getLogger(XSSFServiceImpl.class);
    private static final String FILE_NAME = "Statistics-";
    private static final String FILE_FORMAT = ".xlsx";
    private AnalyzerService analyzerService;

    /**
     * Initialize <code>XSSFServiceImpl</code> object with {@link AnalyzerService} instance
     * thus providing means to generate and retrieve {@link Statistics} object.
     *
     * @param analyzerService provider of {@link Statistics} object.
     */
    public XSSFServiceImpl(AnalyzerService analyzerService) {
        this.analyzerService = analyzerService;
    }

    /**
     * Using provided {@link ArraySpawnStrategy} writes {@link Statistics}
     * in form of excel file and saves it by specified path.
     *
     * @param strategy array spawn strategy.
     * @param path     path to save the statistics file.
     */
    @Override
    public void writeStatistics(ArraySpawnStrategy strategy, Path path) {
        Statistics statistics = this.analyzerService.generateStatistics(strategy);
        this.writeAsXLSX(statistics, path);
    }

    /**
     * <p>
     * Creates excel workbook {@link XSSFWorkbook} and using provided {@link Statistics}
     * objects, for each {@link Fill} object creates new {@link XSSFSheet} sheet, on each sheet
     * using {@link SortStatistics} object writes {@link Record} for each sort method, containing
     * information about size of the array to sort and CPU time to compute the sort.
     * </p>
     * <p>
     * For each {@link Fill} object draws line chart on each sort method that describes
     * the relationship between the running time of the sort and the number of elements in the array.
     * </p>
     *
     * @param statistics statistics object to process.
     * @param path       path to save the statistics file.
     */
    private void writeAsXLSX(Statistics statistics, Path path) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        LOG.info("Creating XSSFWorkbook...");

        for (String fillName : statistics.getFillNames()) {
            XSSFSheet sheet = workbook.createSheet(fillName);
            LOG.debug("Creating sheet for: {}", fillName);

            SortStatistics sortStatistics = statistics.getSortStatisticsByFillName(fillName);
            Set<String> sortNames = sortStatistics.getSortNames();
            LOG.debug("For {} retrieved: {} Sorts", fillName, sortNames.size());

            int sortSignatureRowIndex = 1;
            int dataColumnIndex = 0;
            for (String sortName : sortNames) {
                XSSFRow elapsedTimeRow = this.writeSortSignature(sheet, sortName, sortSignatureRowIndex);
                XSSFRow elementsRow = sheet.createRow(0); // elements row is shared for all sorts

                dataColumnIndex = this.writeStatisticsRow(sortStatistics, sortName, elapsedTimeRow, elementsRow);
                sortSignatureRowIndex++;
            }
            LOG.debug("Drawing chart for {}", fillName);
            this.drawChart(sheet, -- sortSignatureRowIndex, -- dataColumnIndex);
        }
        this.saveWorkbook(workbook, path.resolve(FILE_NAME + LocalDateTime.now() + FILE_FORMAT));
    }

    /**
     * Writes signature of the {@link Sort} object to provided {@link XSSFSheet},
     * using provided row index.
     *
     * @param sheet                 {@link XSSFSheet} to write the signature on.
     * @param sortName              signature of {@link Sort} object.
     * @param sortSignatureRowIndex index ot the row to write the signature on.
     * @return row on which the signature was written.
     */
    private XSSFRow writeSortSignature(XSSFSheet sheet, String sortName, Integer sortSignatureRowIndex) {
        XSSFRow row = sheet.createRow(sortSignatureRowIndex);
        XSSFCell cell = row.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(sortName);
        return row;
    }

    /**
     * <p>
     * Writes statistics record on single {@link Sort} sort type identified
     * by provided <em>sortName</em>.
     * </p>
     * <p>
     * Record is written in two rows, one contains number of elements in each
     * array, the other row contains the elapsed CPU time to compute the sort.
     * </p>
     *
     * @param sortStatistics sortStatistics to write.
     * @param sortName       name of specific {@link Sort} implementation.
     * @param elapsedTimeRow row to write elapsed time in.
     * @param elementsRow    row to write array capacity in.
     * @return number of columns containing data.
     */
    private Integer writeStatisticsRow(SortStatistics sortStatistics,
                                       String sortName,
                                       XSSFRow elapsedTimeRow,
                                       XSSFRow elementsRow) {
        int elapsedTimeCollIndex = 1;
        int numberOfElementsCollIndex = 1;
        List<Record> records = sortStatistics.getRecordsBySortName(sortName);
        LOG.debug("For {} retrieved: {} Records", sortName, records.size());
        for (Record record : records) {
            this.writeNumberOfElements(elementsRow, record.getNumberOfElements(), numberOfElementsCollIndex++);
            this.writeElapsedTime(elapsedTimeRow, record.getElapsedTime(), elapsedTimeCollIndex++);
        }
        return numberOfElementsCollIndex;
    }

    /**
     * Writes array sizes for each {@link Sort}.
     *
     * @param firstRow                  the row to write the array sizes to.
     * @param numberOfElements          specific array size record.
     * @param numberOfElementsCollIndex index of the column to the write record to.
     */
    private void writeNumberOfElements(XSSFRow firstRow, Integer numberOfElements, Integer numberOfElementsCollIndex) {
        XSSFCell cell2 = firstRow.createCell(numberOfElementsCollIndex);
        cell2.setCellType(CellType.NUMERIC);
        cell2.setCellValue(numberOfElements);
    }

    /**
     * Writes elapsed time for each {@link Sort}.
     *
     * @param row                  the row to write the elapsed time to.
     * @param elapsedTime          specific elapsed time record.
     * @param elapsedTimeCollIndex index of the column to write the record to.
     */
    private void writeElapsedTime(XSSFRow row, Long elapsedTime, Integer elapsedTimeCollIndex) {
        XSSFCell cell1 = row.createCell(elapsedTimeCollIndex);
        cell1.setCellType(CellType.NUMERIC);
        cell1.setCellValue(elapsedTime);
    }

    /**
     * Using provided sheet and number of rows and columns containing data
     * draws line charts.
     *
     * @param sheet     sheet containing data.
     * @param rowIndex  index of the row containing data.
     * @param collCount number of columns containing data.
     */
    private void drawChart(XSSFSheet sheet, Integer rowIndex, Integer collCount) {
        XSSFChart chart = this.createChart(sheet);

        // Create data for the chart
        ScatterChartData data = chart.getChartDataFactory().createScatterChartData();

        // Define chart AXIS
        ChartAxis bottomAxis = chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
        ValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
        leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);

        // add chart series for each sort
        this.addChartSeries(sheet, data, rowIndex, collCount);

        // Plot the chart with the inputs from data and chart axis
        chart.plot(data, bottomAxis, leftAxis);
    }

    /**
     * Creates {@link XSSFChart} line chart for given {@link XSSFSheet} workbook sheet.
     *
     * @param sheet sheet to create chart on.
     * @return chart.
     */
    private XSSFChart createChart(XSSFSheet sheet) {
        // Create a drawing canvas on the worksheet
        XSSFDrawing drawing = sheet.createDrawingPatriarch();

        // Define anchor points in the worksheet to position the chart
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 15, 10, 25);

        // Create the chart object based on the anchor point
        XSSFChart chart = drawing.createChart(anchor);

        // Define legends for the line chart and set the position of the legend
        XSSFChartLegend legend = chart.getOrCreateLegend();
        legend.setPosition(LegendPosition.BOTTOM);
        return chart;
    }

    /**
     * Adds chart series for each {@link Sort}.
     *
     * @param sheet     sheet containing data.
     * @param data      data to write the series.
     * @param rowIndex  index of the row containing data.
     * @param collCount number of columns containing data.
     */
    private void addChartSeries(XSSFSheet sheet, ScatterChartData data, Integer rowIndex, Integer collCount) {
        ChartDataSource<Number> x = this.getNumberOfElementsDataSource(sheet, collCount);
        while (rowIndex > 0) {
            ChartDataSource<Number> y = this.getElapsedTimeDataSource(sheet, rowIndex, collCount);
            ScatterChartSeries series = data.addSerie(x, y);

            this.setSeriesSortSignature(sheet, series, rowIndex);
            -- rowIndex;
        }
    }

    /**
     * Selects data range containing array sizes.
     *
     * @param sheet     sheet containing data.
     * @param collCount number of columns containing data.
     * @return data range of array sizes.
     */
    private ChartDataSource<Number> getNumberOfElementsDataSource(XSSFSheet sheet, int collCount) {
        return DataSources.fromNumericCellRange(sheet, new CellRangeAddress(0, 0, 1, collCount));
    }

    /**
     * Selects data range containing information about elapsed time for each sort.
     *
     * @param sheet     sheet containing data.
     * @param rowIndex  row index for the specific {@link Sort}
     * @param collCount number of columns containing data.
     * @return data range containing information about elapsed time for each sort
     */
    private ChartDataSource<Number> getElapsedTimeDataSource(XSSFSheet sheet, int rowIndex, int collCount) {
        return DataSources.fromNumericCellRange(sheet, new CellRangeAddress(rowIndex, rowIndex, 1, collCount));
    }

    /**
     * On provided {@link XSSFSheet} writes {@link Sort} signature to specified
     * {@link LineChartData} series object as its title.
     *
     * @param sheet    sheet containing data.
     * @param series   the line series to set the title for.
     * @param rowIndex row index to fetch the signature from.
     */
    private void setSeriesSortSignature(XSSFSheet sheet, ScatterChartSeries series, Integer rowIndex) {
        XSSFRow row = sheet.getRow(rowIndex);
        XSSFCell cell = row.getCell(0);
        String sortSignature = cell.getStringCellValue();
        series.setTitle(sortSignature);
        LOG.debug("Drawing {} line chart", sortSignature);
    }

    /**
     * Persist provided {@link XSSFWorkbook} workbook to file system
     * by provided path.
     *
     * @param workbook workbook to save.
     * @param path     path to save the workbook by.
     */
    private void saveWorkbook(XSSFWorkbook workbook, Path path) {
        try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(path, CREATE))) {
            LOG.debug("Writing statistics to file: {}", path.getFileName());
            workbook.write(out);
            out.flush();
        } catch (IOException e) {
            throw new StatisticStorageException(path, e);
        }
    }
}
