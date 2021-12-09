package com.pusio.day9.part2;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.pusio.utils.Utils;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProblemSolver {
    @SneakyThrows
    public Long solve(String filePath) {
        List<String> stringLines = Utils.readLines(filePath);
        Table<Integer, Integer, Pair<Integer, Boolean>> map = parseMap(stringLines);
        List<Integer> result = new ArrayList<>();

        List<Table.Cell<Integer, Integer, Pair<Integer, Boolean>>> deepestCells = new ArrayList<>();

        map.cellSet()
                .stream()
                .filter(cell -> isDeeperThanSurroundings(map, cell.getRowKey(), cell.getColumnKey()))
                .forEach(deepestCells::add);

        for (Table.Cell<Integer, Integer, Pair<Integer, Boolean>> deepestCell : deepestCells) {
            AtomicInteger counter = new AtomicInteger();
            recursiveMapExploration(deepestCell.getRowKey(), deepestCell.getColumnKey(), HashBasedTable.create(map), counter);
            result.add(counter.get());
        }
        return result.stream()
                .sorted(Comparator.reverseOrder())
                .mapToLong(a -> a)
                .limit(3)
                .reduce(1, (a, b) -> a * b);
    }

    private boolean isDeeperThanSurroundings(Table<Integer, Integer, Pair<Integer, Boolean>> map, Integer y, Integer x) {
        if (map.get(y, x) == null) {
            return false;
        }
        Integer point = map.get(y, x).getLeft();
        boolean left = isHigher(map.get(y - 1, x), point);
        boolean right = isHigher(map.get(y + 1, x), point);
        boolean up = isHigher(map.get(y, x + 1), point);
        boolean down = isHigher(map.get(y, x - 1), point);
        return left && right && up && down;
    }

    private void recursiveMapExploration(Integer y, Integer x, Table<Integer, Integer, Pair<Integer, Boolean>> map, AtomicInteger counter) {
        if (map.get(y, x) != null && map.get(y, x).getLeft() < 9 && map.get(y, x).getRight() == false) {
            counter.addAndGet(1);
            map.put(y, x, Pair.of(map.get(y, x).getLeft(), true));

            recursiveMapExploration(y - 1, x, map, counter);
            recursiveMapExploration(y + 1, x, map, counter);
            recursiveMapExploration(y, x - 1, map, counter);
            recursiveMapExploration(y, x + 1, map, counter);
        }
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
