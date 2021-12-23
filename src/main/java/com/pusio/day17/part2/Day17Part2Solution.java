package com.pusio.day17.part2;

import com.pusio.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Day17Part2Solution {
    private Point rectStart;
    private Point rectEnd;
    private Set<Point> tried = new HashSet<>();
    private Set<Point> successful = new HashSet<>();

    public Long solve(String filePath) {
        parsePositions(filePath);

        Integer maxy = 0;
        Queue<Point> queue = new LinkedList<>();
        Queue<Point> betterQueue = new LinkedList<>();
        queue.add(new Point(0, 1));
        queue.add(new Point(1, 0));

        for (int ignore = 0; ignore < 80000; ignore++) {
            Point throwingScallar;

            //ugluy optimalisation probably not needed
            if (betterQueue.isEmpty()) {
                throwingScallar = queue.poll();

            } else {
                throwingScallar = betterQueue.poll();
            }
            Pair<Position, Integer> maxYAndHitResult = throwItWith(throwingScallar);
            if (maxYAndHitResult.getLeft() == Position.INSIDE && maxYAndHitResult.getRight() > maxy) {
                maxy = maxYAndHitResult.getRight();
            }
            Point point1 = new Point(throwingScallar.getX() + 1, throwingScallar.getY());
            Point point2 = new Point(throwingScallar.getX(), throwingScallar.getY() + 1);
            Point point3 = new Point(throwingScallar.getX() - 1, throwingScallar.getY());
            Point point4 = new Point(throwingScallar.getX(), throwingScallar.getY() - 1);
            if (maxYAndHitResult.getLeft() == Position.INSIDE) {
                successful.add(throwingScallar);
                System.out.println("SIZE: " + successful.size());
                putIfPossible(betterQueue, point1);
                putIfPossible(betterQueue, point2);
                putIfPossible(betterQueue, point3);
                putIfPossible(betterQueue, point4);
            } else {
                putIfPossible(queue, point1);
                putIfPossible(queue, point2);
                putIfPossible(queue, point3);
                putIfPossible(queue, point4);
            }
        }
        return Long.valueOf(successful.size());
    }

    private void putIfPossible(Queue<Point> betterQueue, Point point) {
        if (!tried.contains(point) && point.getX() <= rectEnd.getX()) {
            betterQueue.add(point);
            tried.add(point);
        }
    }

    private Pair<Position, Integer> throwItWith(Point throwingScalar) {
        Integer maxy = 0;
        Point throwingPoint = new Point(0, 0);
        while (checkPosition(throwingPoint) == Position.BEFORE) {
            throwingPoint = new Point(throwingPoint.getX() + throwingScalar.getX(), throwingPoint.getY() + throwingScalar.getY());
            if (throwingPoint.getY() > maxy) {
                maxy = throwingPoint.getY();
            }
            int posYScalar = throwingScalar.getY();
            int posXScalar = throwingScalar.getX();
            if (posXScalar > 0) {
                posXScalar -= 1;
            } else if (posXScalar < 0) {
                posXScalar += 1;
            }
            posYScalar -= 1;
            throwingScalar = new Point(posXScalar, posYScalar);
        }
        return Pair.of(checkPosition(throwingPoint), maxy);
    }

    private void parsePositions(String filePath) {
        String line = Utils.readLines(filePath).stream().findFirst().orElseThrow();
        String s = line.replaceAll("target area: ", "");
        String[] strings = StringUtils.splitByCharacterType(s);
        rectStart = new Point(Integer.valueOf(strings[2]), Integer.valueOf(strings[13]) * -1);
        rectEnd = new Point(Integer.valueOf(strings[4]), Integer.valueOf(strings[10]) * -1);

        System.out.println("start: " + rectStart);
        System.out.println("end: " + rectEnd);
    }

    public Position checkPosition(Point point) {
        if (point.getX() >= rectStart.getX() && point.getX() <= rectEnd.getX() &&
                point.getY() <= rectStart.getY() && point.getY() >= rectEnd.getY()) {
            return Position.INSIDE;
        }
        if (point.getX() > rectEnd.getX() || point.getY() < rectEnd.getY()) {
            return Position.AFTER;
        }
        return Position.BEFORE;
    }

    private enum Position {
        BEFORE,
        INSIDE,
        AFTER
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