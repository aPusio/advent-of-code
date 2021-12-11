package com.pusio.day11.part1;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.pusio.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProblemSolver {
    private Long result = 0L;
    Table<Integer, Integer, Integer> map;

    public Long solve(String filePath) {
        List<String> lines = Utils.readLines(filePath);
        map = parseMap(lines);
        IntStream.range(0, 100)
                .peek(this::printMap).
                forEach(step -> generateStep());

        return result;
    }

    private void printMap(int i) {
        System.out.println("STEP: " + i);
        for (Integer y : map.rowKeySet()) {
            for (Integer x : map.columnKeySet()) {
                Integer integer = map.get(x, y);
                if (integer > 9) {
                    System.out.print("*");
                } else {
                    System.out.print(integer);
                }
            }
            System.out.println();
        }
    }

    private void generateStep() {
        Table<Integer, Integer, Boolean> visited = HashBasedTable.create();

        map.cellSet()
                .stream()
                .map(Point::new)
                .forEach(this::increasePointvalue);
        map.cellSet()
                .stream()
                .map(Point::new)
                .forEach(point -> doBoom(point, visited));
    }

    private void doBoom(Point point, Table<Integer, Integer, Boolean> visited) {
        if (map.get(point.y, point.x) > 9) {
            System.out.println(point + " is bigger than 9");
            zeroPointValue(point);
            visited.put(point.y, point.x, true);
            getSurroundings(point, visited)
                    .stream()
                    .map(Point::new)
                    .peek(this::increasePointvalue)
                    .forEach(point1 -> doBoom(point1, visited));

            result += 1;
        }
    }

    private Integer increasePointvalue(Point point) {
        return map.put(point.y, point.x, map.get(point.y, point.x) + 1);
    }

    private Integer zeroPointValue(Point point) {
        return map.put(point.y, point.x, 0);
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

    private List<Table.Cell<Integer, Integer, Integer>> getSurroundings(Point startCell, Table<Integer, Integer, Boolean> visited) {
        getPointIfNotVisited(startCell, visited);

        return map.cellSet()
                .stream()
                .filter(cell ->
                        visited.get(startCell.y - 1, startCell.x) != null && isOnSamePosition(cell, startCell.y - 1, startCell.x) ||
                                visited.get(startCell.y + 1, startCell.x) != null && isOnSamePosition(cell, startCell.y + 1, startCell.x) ||
                                visited.get(startCell.y, startCell.x - 1) != null && isOnSamePosition(cell, startCell.y, startCell.x - 1) ||
                                visited.get(startCell.y, startCell.x + 1) != null && isOnSamePosition(cell, startCell.y, startCell.x + 1) ||
                                visited.get(startCell.y + 1, startCell.x + 1) != null && isOnSamePosition(cell, startCell.y + 1, startCell.x + 1) ||
                                visited.get(startCell.y - 1, startCell.x - 1) != null && isOnSamePosition(cell, startCell.y - 1, startCell.x - 1) ||
                                visited.get(startCell.y - 1, startCell.x + 1) != null && isOnSamePosition(cell, startCell.y - 1, startCell.x + 1) ||
                                visited.get(startCell.y + 1, startCell.x - 1) != null && isOnSamePosition(cell, startCell.y + 1, startCell.x - 1)
                )
                .collect(Collectors.toList());


//        return Stream.of(
//                        map.get(cell.getRowKey() - 1, cell.getColumnKey()),
//                        map.get(cell.getRowKey() + 1, cell.getColumnKey()),
//                        map.get(cell.getRowKey(), cell.getColumnKey() + 1),
//                        map.get(cell.getRowKey(), cell.getColumnKey() - 1),
//                        map.get(cell.getRowKey()-1, cell.getColumnKey()-1),
//                        map.get(cell.getRowKey()+1, cell.getColumnKey()+1),
//                        map.get(cell.getRowKey()-1, cell.getColumnKey()+1),
//                        map.get(cell.getRowKey()+1, cell.getColumnKey()-1)
//                )
//                .filter(Objects::nonNull);
    }

    private void getPointIfNotVisited(Point startCell, Table<Integer, Integer, Boolean> visited) {
        if (visited.get(startCell.y, startCell.x) == null || visited.get(startCell.y, startCell.x) == false) {
            return null;
        }
        return;

    }

//    public List<Table.Cell<Integer, Integer, Integer, Boolean>> getNotVisitedSurroundings(Table.Cell<Integer, Integer, Integer> startCell){
////        getSurroundings(map, startCell)
//        return getSurroundings(map, startCell)
//                .stream()
//                .filter(cell -> !Objects.requireNonNull(cell.getValue()).getRight())
//                .collect(Collectors.toList());
//    }

//    public boolean isOnSamePosition(Table.Cell<Integer, Integer, Integer> cell, Integer y, Integer x) {
//        return Objects.equals(cell.getColumnKey(), y) && Objects.equals(cell.getRowKey(), x);
//    }

    @AllArgsConstructor
    @Getter
    @ToString
    @EqualsAndHashCode
    class Point {
        Integer x;
        Integer y;

        public Point(Table.Cell<Integer, Integer, Integer> cell) {
            x = cell.getColumnKey();
            y = cell.getRowKey();
        }
    }
}
