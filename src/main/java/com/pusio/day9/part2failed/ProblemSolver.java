package com.pusio.day9.part2failed;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.pusio.utils.Utils;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProblemSolver {
    @SneakyThrows
    public Long solve(String filePath) {
        List<String> stringLines = Utils.readLines(filePath);
        Table<Integer, Integer, Pair<Integer, Boolean>> map = parseMap(stringLines);
        List<Integer> result = new ArrayList<>();

        for (Integer y : map.rowKeySet()) {
            for (Integer x : map.columnKeySet()) {
                List<Boolean> counter = new ArrayList<>();

                recursiveBasin(y, x, HashBasedTable.create(map), counter);
                result.add(counter.size());
            }
        }
        return result.stream()
                .sorted(Comparator.reverseOrder())
                .mapToLong(a -> a)
                .limit(3)
                .reduce(1, (a, b) -> a * b);
    }

    private boolean isDeeperThanSurrondings(Table<Integer, Integer, Pair<Integer, Boolean>> map, Integer y, Integer x) {
        if (map.get(y, x) == null) {
            return false;
        }
        if (map.get(y, x).getLeft() == 9) {
            return false;
        }
        if (map.get(y, x).getLeft() == 0) {
            return true;
        }
        Integer point = map.get(y, x).getLeft();
        boolean left = isHigher(map.get(y - 1, x), point);
        boolean right = isHigher(map.get(y + 1, x), point);
        boolean up = isHigher(map.get(y, x + 1), point);
        boolean down = isHigher(map.get(y, x - 1), point);
        return left && right && up && down;
    }

    private Table<Integer, Integer, Pair<Integer, Boolean>> recursiveBasin(Integer y, Integer x, Table<Integer, Integer, Pair<Integer, Boolean>> map, List<Boolean> counter) {
        if (isDeeperThanSurrondings(map, y, x) && map.get(y, x).getRight() == false) {
//            System.out.println("y- " + y + " x- " + x + " val: " + map.get(y,x) + "size: " + counter.size());
            counter.add(true);
            Pair<Integer, Boolean> pair = map.get(y, x);
            map.put(y, x, Pair.of(pair.getLeft(), true));

            recursiveBasin(y - 1, x, map, counter);
            recursiveBasin(y + 1, x, map, counter);
            recursiveBasin(y, x - 1, map, counter);
            recursiveBasin(y, x + 1, map, counter);
        }
        return map;
    }

    private boolean isHigher(Pair<Integer, Boolean> pointToCheck, Integer point) {
        if (pointToCheck == null) {
            return true;
        }
        if (pointToCheck.getRight()) {
            return true;
        }
        return pointToCheck.getLeft() > point;
    }

    private Table<Integer, Integer, Pair<Integer, Boolean>> parseMap(List<String> stringLines) {
        Table<Integer, Integer, Pair<Integer, Boolean>> map = HashBasedTable.create();
        for (int y = 0; y < stringLines.size(); y++) {
            String line = stringLines.get(y);
            for (int x = 0; x < line.length(); x++) {
                map.put(x, y, Pair.of(Integer.valueOf(String.valueOf(line.charAt(x))), false));
            }
        }
        return map;
    }
}
