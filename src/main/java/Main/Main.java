package Main;


import Channels.DataChannel;
import Database.DataFetcher;
import Database.AnotherDataFetcher;
import Filters.DataNormalizationFilter;
import Filters.OutlierRemovalFilter;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import Report.BoxPlotGenerator;
import Filters.DataValidatorFilter;
import Report.ReportGenerator;
import Stat.StatisticsCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        DataChannel dataChannel = new DataChannel();

        List<int[]> rawData = DataFetcher.fetchData();
        List<int[]> anotherRawData = AnotherDataFetcher.fetchData();
        List<int[]> combinedData = new ArrayList<>();
        combinedData.addAll(rawData);
        combinedData.addAll(anotherRawData);

        List<int[]> validatedData = DataValidatorFilter.validateData(combinedData);
        List<int[]> filteredData = OutlierRemovalFilter.removeOutliers(validatedData);
        List<int[]> normalizedData = DataNormalizationFilter.normalizeData(filteredData);
        dataChannel.setData(normalizedData);

        List<int[]> data = dataChannel.getData();
        List<DescriptiveStatistics> statsList = new ArrayList<>();
        String[] years = {"2023", "2024", "2025"};
        String[] imagePaths = {"2023.png", "2024.png", "2025.png"};

        for (int i = 0; i < 3; i++) {
            final int yearIndex = i; // Make index effectively final
            List<Integer> yearData = data.stream().map(row -> row[yearIndex]).collect(Collectors.toList());
            DescriptiveStatistics stats = StatisticsCalculator.calculateStats(yearData);
            statsList.add(stats);

            BoxPlotGenerator.generateBoxPlot(yearData, "Year " + years[yearIndex], years[yearIndex], imagePaths[yearIndex]);
        }

        ReportGenerator.generateReport(statsList, years, imagePaths, "report.docx");
    }
}
