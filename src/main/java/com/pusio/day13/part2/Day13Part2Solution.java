package com.pusio.day13.part2;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.pusio.utils.Utils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day13Part2Solution {
    private List<Pair<String, Integer>> folds = new ArrayList<>();
    private Table<Integer, Integer, Boolean> mapOfDots = HashBasedTable.create();

    public Long solve(String filePath) {
        List<String> strings = Utils.readLines(filePath);
        readInput(strings);
        System.out.println(mapOfDots);
        for (Pair<String, Integer> fold : folds) {
            if (fold.getLeft().equals("y")) {
                List<Table.Cell<Integer, Integer, Boolean>> cellsToFold = mapOfDots.cellSet()
                        .stream()
                        .filter(cell -> cell.getRowKey() > fold.getRight())
                        .collect(Collectors.toList());

                cellsToFold.forEach(cell -> {
                    mapOfDots.remove(cell.getRowKey(), cell.getColumnKey());
                    mapOfDots.put(cell.getRowKey() - ((cell.getRowKey() - fold.getRight()) * 2), cell.getColumnKey(), true);
                });
            } else {
                List<Table.Cell<Integer, Integer, Boolean>> cellsToFold = mapOfDots.cellSet()
                        .stream()
                        .filter(cell -> cell.getColumnKey() > fold.getRight())
                        .collect(Collectors.toList());

                cellsToFold.forEach(cell -> {
                    mapOfDots.remove(cell.getRowKey(), cell.getColumnKey());
                    mapOfDots.put(cell.getRowKey(), cell.getColumnKey() - ((cell.getColumnKey() - fold.getRight()) * 2), true);
                });
            }
        }
        int maxRow = mapOfDots.rowKeySet().stream().mapToInt(a -> a).max().orElseThrow();
        int maxCollumn = mapOfDots.columnKeySet().stream().mapToInt(a -> a).max().orElseThrow();
        for (int y = 0; y <= maxRow; y++) {
            for (int x = 0; x <= maxCollumn; x++) {
                if (mapOfDots.get(y, x) == null) {
                    System.out.print(" ");
                } else {
                    System.out.print("#");
                }
            }
            System.out.println();
        }


        System.out.println(mapOfDots);
        System.out.println(folds);
        return Long.valueOf(mapOfDots.size());
    }

    private void readInput(List<String> strings) {
        boolean readingDots = true;
        for (String line : strings) {
            if (line.isEmpty()) {
                readingDots = false;
            }
            if (readingDots) {
                String[] split = line.split(",");
                mapOfDots.put(Integer.valueOf(split[1]), Integer.valueOf(split[0]), true);
            } else if (!line.isEmpty()) {
                String[] s = line.split(" ");
                String[] split = s[2].split("=");
                folds.add(Pair.of(split[0], Integer.valueOf(split[1])));
            }
        }
    }
}
