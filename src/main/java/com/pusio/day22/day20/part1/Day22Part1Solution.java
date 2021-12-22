package com.pusio.day22.day20.part1;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;
import com.pusio.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class Day22Part1Solution {

    Set<Point> uniquePoints = new HashSet<>();

    private static final Integer MIN_POSITION = -50;
    private static final Integer MAX_POSITION = 50;

    public Long solve(String filePath) {
        Utils.readLines(filePath).forEach(line -> {
            String[] splitBySpace = line.split(" ");
            boolean isOn = splitBySpace[0].equals("on");
            String[] ranges = splitBySpace[1].split(",");
            String[] splitX = StringUtils.stripStart(ranges[0], "x=").replace("..", " ").split(" ");
            Integer startX = Integer.parseInt(splitX[0]);
            Integer endX = Integer.parseInt(splitX[1]);
            String[] splitY = StringUtils.stripStart(ranges[1], "y=").replace("..", " ").split(" ");
            Integer startY = Integer.parseInt(splitY[0]);
            Integer endY = Integer.parseInt(splitY[1]);
            String[] splitZ = StringUtils.stripStart(ranges[2], "z=").replace("..", " ").split(" ");
            Integer startZ = Integer.parseInt(splitZ[0]);
            Integer endZ = Integer.parseInt(splitZ[1]);
            Ranges map = new Ranges();
            if (isInRange(startX, endX, startY, endY, startZ, endZ)) {
//                if (isOn) {
                map.getX().add(Range.closed(startX, endX));
                map.getY().add(Range.closed(startY, endY));
                map.getZ().add(Range.closed(startZ, endZ));
//                } else {
//                    map.getX().remove(Range.closed(startX, endX));
//                    map.getY().remove(Range.closed(startY, endY));
//                    map.getZ().remove(Range.closed(startZ, endZ));
//                }
                map.appendToUniqueSet(isOn);

                System.out.println("sum: " + uniquePoints.size());

            }
        });

        return (long) uniquePoints.size();
    }

    private Long sumOfRange(RangeSet<Integer> rangeSet) {
//        RangeSet<Integer> rangeSet = map.getX();
        Long sum = 0L;
        for (Range<Integer> rangeX : rangeSet.asRanges()) {
            Integer lower = rangeX.lowerEndpoint();
            Integer upper = rangeX.upperEndpoint();
            int abs = Math.abs(upper - lower);
            sum += abs;
        }
        return sum;
    }

    private boolean isInRange(Integer startX, Integer endX, Integer startY, Integer endY, Integer startZ, Integer endZ) {
        return startX >= MIN_POSITION && startY >= MIN_POSITION && startZ >= MIN_POSITION && endX <= MAX_POSITION && endY <= MAX_POSITION && endZ <= MAX_POSITION;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @EqualsAndHashCode
    private class Ranges {
        private final RangeSet<Integer> x = TreeRangeSet.create();
        private final RangeSet<Integer> y = TreeRangeSet.create();
        private final RangeSet<Integer> z = TreeRangeSet.create();

        public int appendToUniqueSet(boolean isOn) {

            for (Range<Integer> rangeX : x.asRanges()) {
                IntStream.rangeClosed(rangeX.lowerEndpoint(), rangeX.upperEndpoint()).forEach(positionX -> {
                    for (Range<Integer> rangeY : y.asRanges()) {
                        IntStream.rangeClosed(rangeY.lowerEndpoint(), rangeY.upperEndpoint()).forEach(positionY -> {
                            for (Range<Integer> rangeZ : z.asRanges()) {
                                IntStream.rangeClosed(rangeZ.lowerEndpoint(), rangeZ.upperEndpoint()).forEach(positionZ -> {
                                    if (isOn) {
                                        uniquePoints.add(new Point(positionX, positionY, positionZ));
                                    } else {
                                        uniquePoints.remove(new Point(positionX, positionY, positionZ));
                                    }
                                });
                            }
                        });
                    }
                });
            }

            return uniquePoints.size();
        }
    }


    @Getter
    @ToString
    @AllArgsConstructor
    @EqualsAndHashCode
    private class Point {
        private final int y;
        private final int x;
        private final int z;
    }
}