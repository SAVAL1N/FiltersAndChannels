package Report;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.CategoryLineAnnotation;
import org.jfree.chart.annotations.CategoryTextAnnotation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class BoxPlotGenerator {

    public static void generateBoxPlot(List<Integer> data, String title, String category, String filePath) throws Exception {
        DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
        List<Number> dataAsNumber = data.stream().map(Number::intValue).collect(Collectors.toList());
        dataset.add(dataAsNumber, "Values", category);

        JFreeChart chart = ChartFactory.createBoxAndWhiskerChart(
                title,
                "Category",
                "Value",
                dataset,
                false
        );

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
        renderer.setMeanVisible(false);
        renderer.setMedianVisible(true);
        renderer.setWhiskerWidth(0.5);
        renderer.setSeriesPaint(0, new Color(0, 102, 204));
        renderer.setSeriesFillPaint(0, new Color(102, 178, 255));
        renderer.setUseOutlinePaintForWhiskers(true);

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.GRAY);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setOutlineVisible(true);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));

        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        yAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        yAxis.setLabelFont(new Font("SansSerif", Font.PLAIN, 14));

        CategoryAxis xAxis = plot.getDomainAxis();
        xAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        xAxis.setLabelFont(new Font("SansSerif", Font.PLAIN, 14));

        addAnnotations(plot, dataAsNumber, category);

        ChartUtils.saveChartAsPNG(new File(filePath), chart, 800, 600);
    }

    private static void addAnnotations(CategoryPlot plot, List<Number> data, String category) {
        double max = data.stream().mapToDouble(Number::doubleValue).max().orElse(0);
        double min = data.stream().mapToDouble(Number::doubleValue).min().orElse(0);
        double q1 = calculatePercentile(data, 25);
        double q3 = calculatePercentile(data, 75);
        double median = calculatePercentile(data, 50);
        double mean = data.stream().mapToDouble(Number::doubleValue).average().orElse(0);

        addLineAndTextAnnotation(plot, "Верхняя граница (максимум)", max, category);
        addLineAndTextAnnotation(plot, "Верхний квартиль (третий кв.)", q3, category);
        addLineAndTextAnnotation(plot, "Среднее", mean, category);
        addLineAndTextAnnotation(plot, "Медиана", median, category);
        addLineAndTextAnnotation(plot, "Нижний квартиль (первый кв.)", q1, category);
        addLineAndTextAnnotation(plot, "Нижняя граница (минимум)", min, category);
    }

    private static void addLineAndTextAnnotation(CategoryPlot plot, String text, double value, String category) {
        CategoryTextAnnotation textAnnotation = new CategoryTextAnnotation(text, category, value);
        textAnnotation.setFont(new Font("SansSerif", Font.PLAIN, 10));
        textAnnotation.setTextAnchor(TextAnchor.BASELINE_LEFT);

        CategoryLineAnnotation lineAnnotation = new CategoryLineAnnotation(category, value, category, value, Color.BLACK, new BasicStroke(1.0f));

        plot.addAnnotation(textAnnotation);
        plot.addAnnotation(lineAnnotation);
    }

    private static double calculatePercentile(List<Number> data, double percentile) {
        if (data.isEmpty()) {
            return 0;
        }
        List<Number> sortedData = data.stream().sorted().collect(Collectors.toList());
        int index = (int) Math.ceil(percentile / 100.0 * sortedData.size());
        if (index <= 0) {
            return sortedData.get(0).doubleValue();
        } else if (index >= sortedData.size()) {
            return sortedData.get(sortedData.size() - 1).doubleValue();
        } else {
            return sortedData.get(index - 1).doubleValue();
        }
    }
}
