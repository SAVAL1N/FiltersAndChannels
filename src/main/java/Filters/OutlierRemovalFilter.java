package Filters;

import java.util.List;
import java.util.stream.Collectors;

public class OutlierRemovalFilter {

    public static List<int[]> removeOutliers(List<int[]> data) {
        return data.stream()
                .filter(row -> row[0] > 10 && row[1] > 10 && row[2] > 10)
                .collect(Collectors.toList());
    }
}
