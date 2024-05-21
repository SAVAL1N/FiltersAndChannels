package Stat;


import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.List;

public class StatisticsCalculator {

    public static DescriptiveStatistics calculateStats(List<Integer> data) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (int value : data) {
            stats.addValue(value);
        }
        return stats;
    }
}
