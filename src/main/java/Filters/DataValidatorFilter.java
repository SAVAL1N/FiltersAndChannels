package Filters;


import java.util.List;
import java.util.stream.Collectors;

public class DataValidatorFilter {

    public static List<int[]> validateData(List<int[]> data) {
        return data.stream().filter(row -> row.length == 3).collect(Collectors.toList());
    }
}