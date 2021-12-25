package com.pusio.day25.part1;

import com.google.common.base.Splitter;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.pusio.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day25Part1Solution {
    private static int SIZE_X;
    private static int SIZE_Y;
    private static final String RIGHT = ">";
    private static final String EMPTY = ".";
    private static final String BOT = "v";

    public Long solve(String filePath) {
        ImmutableTable<Integer, Integer, String> map = initMap(filePath);
        printMap(map, 0);

        boolean anyCucumberMoved;
        Long counter = 0L;
        do {
            anyCucumberMoved = false;
            Table<Integer, Integer, String> nextStepMap = HashBasedTable.create(map);
            for (Table.Cell<Integer, Integer, String> cucumber : filterCells(map, RIGHT)) {
                Point next = getNext(cucumber);
                if (map.get(next.getY(), next.getX()).equals(EMPTY)) {
                    nextStepMap.put(cucumber.getRowKey(), cucumber.getColumnKey(), EMPTY);
                    nextStepMap.put(next.getY(), next.getX(), RIGHT);
                    anyCucumberMoved = true;
                }
            }
            map = ImmutableTable.copyOf(nextStepMap);

            List<Table.Cell<Integer, Integer, String>> botMoving = filterCells(map, BOT);
            for (Table.Cell<Integer, Integer, String> cucumber : botMoving) {
                Point next = getNext(cucumber);
                if (map.get(next.getY(), next.getX()).equals(EMPTY)) {
                    nextStepMap.put(cucumber.getRowKey(), cucumber.getColumnKey(), EMPTY);
                    nextStepMap.put(next.getY(), next.getX(), BOT);
                    anyCucumberMoved = true;
                }
            }
            map = ImmutableTable.copyOf(nextStepMap);
            counter++;
        } while (anyCucumberMoved);

        return counter;
    }

    private void printMap(Table<Integer, Integer, String> map, int a) {
        System.out.println("----MAP---- " + a);
        for (Integer y : map.rowKeySet()) {
            for (Integer x : map.columnKeySet()) {
                String s = map.get(y, x);
                System.out.print(s);
            }
            System.out.println();
        }
    }

    private List<Table.Cell<Integer, Integer, String>> filterCells(ImmutableTable<Integer, Integer, String> map, String s) {
        List<Table.Cell<Integer, Integer, String>> rightMoving = map.cellSet()
                .stream()
                .filter(cell -> Objects.equals(cell.getValue(), s))
                .collect(Collectors.toList());
        return rightMoving;
    }

    private Point getNext(Table.Cell<Integer, Integer, String> cucumber) {
        Integer y = cucumber.getRowKey();
        Integer x = cucumber.getColumnKey();
        if (cucumber.getValue().equals(RIGHT)) {
            return new Point(x + 1 > SIZE_X ? 0 : x + 1, y);
        } else if (cucumber.getValue().equals(BOT)) {
            return new Point(x, y + 1 > SIZE_Y ? 0 : y + 1);
        }
        return null;
    }

    private ImmutableTable<Integer, Integer, String> initMap(String filePath) {
        ImmutableTable.Builder<Integer, Integer, String> builder = ImmutableTable.builder();
        List<String> lines = Utils.readLines(filePath);
        for (int y = 0; y < lines.size(); y++) {
            List<String> mapChars = Splitter.fixedLength(1).splitToList(lines.get(y));
            for (int x = 0; x < mapChars.size(); x++) {
                builder.put(y, x, mapChars.get(x));
            }
        }
        ImmutableTable<Integer, Integer, String> map = builder.build();
        SIZE_X = map.columnKeySet().size() - 1;
        SIZE_Y = map.rowKeySet().size() - 1;
        return map;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @EqualsAndHashCode
    private class Point {
        private final int x;
        private final int y;
    }
}