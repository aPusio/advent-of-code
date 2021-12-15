package com.pusio.day15.part1;

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

public class Day15Part1Solution {
    private Table<Integer, Integer, Integer> map = HashBasedTable.create();
    private Table<Integer, Integer, Boolean> visited = HashBasedTable.create();
    private Point finish;

    public Long solve(String filePath) {
        List<String> lines = Utils.readLines(filePath);
        parseMap(lines);
        printMap();


        PriorityQueue<Point> queue = new PriorityQueue<>();
        Point currentPoint = new Point(0, 0, 0L);
        queue.add(currentPoint);
        while (!(currentPoint.getX() == finish.getX() && currentPoint.getY() == finish.getY())) {
            currentPoint = queue.poll();
            visited.put(currentPoint.getY(), currentPoint.getX(), true);
            for (Point surrounding : getSurroundings(currentPoint)) {
                if (visited.get(surrounding.getY(), surrounding.getX()) == null) {
                    queue.add(new Point(surrounding.getY(), surrounding.getX(), surrounding.getValue() + currentPoint.getValue()));
                }
            }
        }
        System.out.println("Queue size: " + queue.size());
        return currentPoint.getValue();
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
        finish = new Point(stringLines.size() - 1, stringLines.get(stringLines.size() - 1).length() - 1, 0L);
        for (int y = 0; y < stringLines.size(); y++) {
            String line = stringLines.get(y);
            for (int x = 0; x < line.length(); x++) {
                map.put(y, x, Integer.valueOf(String.valueOf(line.charAt(x))));
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
