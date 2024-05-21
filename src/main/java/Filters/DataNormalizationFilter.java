package Filters;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataNormalizationFilter {

    public static List<int[]> normalizeData(List<int[]> data) {
        return data.stream()
                .map(row -> Arrays.stream(row).map(value -> (int) Math.round(value)).toArray())
                .collect(Collectors.toList());
    }
}
