package com.pusio.day9.part1;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.pusio.utils.Utils;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

public class ProblemSolver {
    @SneakyThrows
    public Long solve(String filePath) {
        List<String> stringLines = Utils.readLines(filePath);
        Table<Integer, Integer, Integer> map = parseMap(stringLines);
        List<Integer> result = new ArrayList<>();

        for (Integer y : map.rowKeySet()) {
            for (Integer x : map.columnKeySet()) {
                Integer point = map.get(y, x);
                boolean left = isHigher(map.get(y - 1, x), point);
                boolean right = isHigher(map.get(y + 1, x), point);
                boolean up = isHigher(map.get(y, x + 1), point);
                boolean down = isHigher(map.get(y, x - 1), point);
                if (left && right && up && down) {
                    result.add(point + 1);
                }
            }
        }
        return result.stream().mapToLong(a -> a).sum();
    }

    private boolean isHigher(Integer pointToCheck, Integer point) {
        if (pointToCheck == null) {
            return true;
        }
        return pointToCheck > point;
    }

    private Table<Integer, Integer, Integer> parseMap(List<String> stringLines) {
        Table<Integer, Integer, Integer> map = HashBasedTable.create();
        for (int y = 0; y < stringLines.size(); y++) {
            String line = stringLines.get(y);
            for (int x = 0; x < line.length(); x++) {
                map.put(x, y, Integer.valueOf(String.valueOf(line.charAt(x))));
            }
        }
        return map;
    }
}
