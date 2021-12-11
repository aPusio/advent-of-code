package com.pusio.day11.part2;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.pusio.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ProblemSolver {
    private Long result = 0L;
    Table<Integer, Integer, Integer> map;

    public Long solve(String filePath) {
        List<String> lines = Utils.readLines(filePath);
        map = parseMap(lines);
        boolean allOctopusesShine = false;
        Long steps = 0L;
        do {

//            IntStream.range(0, 100)
//                    .peek(this::printMap).
//                    forEach(step -> generateStep());
            generateStep();
            printMap(steps.intValue());
            allOctopusesShine = checkOctopusShining();
            steps++;
        } while (!allOctopusesShine);

        return steps;
    }

    private boolean checkOctopusShining() {
        for (Integer y : map.rowKeySet()) {
            for (Integer x : map.columnKeySet()) {
                if(map.get(y,x) != 0) {
                    return false;
                }
            }
        }
        return true;
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
                    .peek(sur -> System.out.println("sur: " + sur + " " + map.get(sur.getY(), sur.getX())))
                    .forEach(this::increasePointvalue);
            result += 1;
            getSurroundings(point, visited).forEach(pt -> doBoom(pt, visited));
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

    private List<Point> getSurroundings(Point startCell, Table<Integer, Integer, Boolean> visited) {
        return Stream.of(
                        getPointIfNotVisited(visited, startCell.getY() - 1, startCell.getX()),
                        getPointIfNotVisited(visited, startCell.getY() + 1, startCell.getX()),
                        getPointIfNotVisited(visited, startCell.getY(), startCell.getX() - 1),
                        getPointIfNotVisited(visited, startCell.getY(), startCell.getX() + 1),

                        getPointIfNotVisited(visited, startCell.getY() + 1, startCell.getX() + 1),
                        getPointIfNotVisited(visited, startCell.getY() - 1, startCell.getX() - 1),
                        getPointIfNotVisited(visited, startCell.getY() - 1, startCell.getX() + 1),
                        getPointIfNotVisited(visited, startCell.getY() + 1, startCell.getX() - 1)
                )
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Point getPointIfNotVisited(Table<Integer, Integer, Boolean> visited, Integer y, Integer x) {
        if (visited.get(y, x) == null && map.get(y, x) != null) {
            return new Point(y, x);
        }
        return null;
    }

//    public List<Table.Cell<Integer, Integer, Integer, Boolean>> getNotVisitedSurroundings(Table.Cell<Integer, Integer, Integer> startCell){
////        getSurroundings(map, startCell)
//        return getSurroundings(map, startCell)
//                .stream()
//                .filter(cell -> !Objects.requireNonNull(cell.getValue()).getRight())
//                .collect(Collectors.toList());
//    }

    public boolean isOnSamePosition(Table.Cell<Integer, Integer, Integer> cell, Integer y, Integer x) {
        return cell.getColumnKey() == y && cell.getRowKey() == x;
    }

    @AllArgsConstructor
    @Getter
    @ToString
    @EqualsAndHashCode
    class Point {
        Integer y;
        Integer x;

        public Point(Table.Cell<Integer, Integer, Integer> cell) {
            x = cell.getColumnKey();
            y = cell.getRowKey();
        }
    }
}
