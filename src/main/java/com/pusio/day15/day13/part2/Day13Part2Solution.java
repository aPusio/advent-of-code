package com.pusio.day15.day13.part2;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.pusio.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day13Part2Solution {
    private Table<Integer, Integer, Integer> map = HashBasedTable.create();
    private Table<Integer, Integer, Long> shortestPaths = HashBasedTable.create();
    private Point finish;

    public Long solve(String filePath) {
        List<String> lines = Utils.readLines(filePath);
        parseMap(lines);
        fillShortestPaths();
        printMap();

        PriorityQueue<Point> queue = new PriorityQueue<>();
        Point currentPoint = new Point(0, 0, 0L);
        queue.add(currentPoint);
        shortestPaths.put(currentPoint.getY(), currentPoint.getX(), 0L);
        while (!(currentPoint.getX() == finish.getX() && currentPoint.getY() == finish.getY())) {
            currentPoint = queue.poll();
            System.out.println("current: " + currentPoint);
            for (Point surrounding : getSurroundings(currentPoint)) {
                long dostance = currentPoint.getValue() + surrounding.getValue();
                if (dostance < shortestPaths.get(surrounding.getY(), surrounding.getX())) {
                    shortestPaths.put(surrounding.getY(), surrounding.getX(), dostance);
                    queue.add(new Point(surrounding.getY(), surrounding.getX(), dostance));
                }
            }
        }
        System.out.println("Queue size: " + queue.size());
        return currentPoint.getValue();
    }

    private void fillShortestPaths() {
        map.cellSet()
                .forEach(cell -> shortestPaths.put(cell.getRowKey(), cell.getColumnKey(), Long.MAX_VALUE));
    }

    private List<Point> getSurroundings(Point point) {
        return Stream.of(
                        new Point(point.getY() - 1, point.getX(), Long.valueOf(Optional.ofNullable(map.get(point.getY() - 1, point.getX())).orElse(-1))),
                        new Point(point.getY() + 1, point.getX(), Long.valueOf(Optional.ofNullable(map.get(point.getY() + 1, point.getX())).orElse(-1))),
                        new Point(point.getY(), point.getX() - 1, Long.valueOf(Optional.ofNullable(map.get(point.getY(), point.getX() - 1)).orElse(-1))),
                        new Point(point.getY(), point.getX() + 1, Long.valueOf(Optional.ofNullable(map.get(point.getY(), point.getX() + 1)).orElse(-1)))
                )
                .filter(a -> a.getValue() > 0)
                .collect(Collectors.toList());
    }

    private void parseMap(List<String> stringLines) {
        finish = new Point(stringLines.size() * 5 - 1, stringLines.get(stringLines.size() - 1).length() * 5 - 1, 0L);
        for (int y = 0; y < stringLines.size(); y++) {
            String line = stringLines.get(y);
            for (int x = 0; x < line.length(); x++) {
                map.put(y, x, Integer.valueOf(String.valueOf(line.charAt(x))));
            }
        }

        for (int y = 0; y < stringLines.size() * 5; y++) {
            for (int x = 0; x < stringLines.size(); x++) {
                int multiplier = y / stringLines.size();
                Integer originalValue = map.get(y % stringLines.size(), x);
                originalValue = originalValue + multiplier;
                if (originalValue > 9) {
                    originalValue = originalValue % 10 + 1;
                }
                map.put(y, x, originalValue);
            }
        }

        for (int y = 0; y < stringLines.size() * 5; y++) {
            for (int x = 0; x < stringLines.size() * 5; x++) {
                int multiplier = x / stringLines.size();
                Integer originalValue = map.get(y, x % stringLines.size());
                originalValue = originalValue + multiplier;
                if (originalValue > 9) {
                    originalValue = originalValue % 10 + 1;
                }
                map.put(y, x, originalValue);
            }
        }
    }

    private void printMap() {
        for (int y = 0; y < map.rowKeySet().size(); y++) {
            for (int x = 0; x < map.row(y).size(); x++) {
                System.out.print(map.get(y, x));
            }
            System.out.println();
        }
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    private static
    class Point implements Comparable<Point> {
        private int y;
        private int x;
        Long value;

        @Override
        public int compareTo(Point otherPoint) {
            return Long.compare(value, otherPoint.getValue());
        }
    }
}
