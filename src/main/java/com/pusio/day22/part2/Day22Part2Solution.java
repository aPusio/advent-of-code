package com.pusio.day22.part2;

import com.google.common.collect.Range;
import com.pusio.utils.Utils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Day22Part2Solution {
    private List<Ranges> processedRages = new ArrayList<>();


    public Long solve(String filePath) {
        List<Ranges> inputRanges = new ArrayList<>();
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
            inputRanges.add(new Ranges(Range.closed(startX, endX), Range.closed(startY, endY), Range.closed(startZ, endZ), isOn));
        });
        for (Ranges inputRange : inputRanges) {
            List<Ranges> sharedRangesToSubstract = new ArrayList<>();
            for (Ranges processedRange : processedRages) {
                Optional<Ranges> sharedPart = inputRange.getSharedPart(processedRange);
                sharedPart.ifPresent(sharedRangesToSubstract::add);
            }
            processedRages.addAll(sharedRangesToSubstract);
            if (inputRange.isOn) {
                processedRages.add(inputRange);
            }
            long sum = processedRages.stream().map(Ranges::possiblePermutations).mapToLong(a -> a).sum();
            System.out.println("sum: " + sum);
        }
        return processedRages.stream().map(Ranges::possiblePermutations).mapToLong(a -> a).sum();
    }

    @Getter
    @ToString
    @RequiredArgsConstructor
    @EqualsAndHashCode
    private class Ranges {
        private final Range<Integer> x;
        private final Range<Integer> y;
        private final Range<Integer> z;
        private final Boolean isOn;

        public Optional<Ranges> getSharedPart(Ranges processedRange) {
            try {
                Range<Integer> sharedX = x.intersection(processedRange.getX());
                Range<Integer> sharedY = y.intersection(processedRange.getY());
                Range<Integer> sharedZ = z.intersection(processedRange.getZ());
                return Optional.of(new Ranges(sharedX, sharedY, sharedZ, !processedRange.getIsOn()));
            } catch (IllegalArgumentException iae) {
                return Optional.empty();
            }
        }

        public Long possiblePermutations() {
            long value = (Long.valueOf(Math.abs(x.upperEndpoint() - x.lowerEndpoint()) + 1) *
                    Long.valueOf(Math.abs((y.upperEndpoint() - y.lowerEndpoint()) + 1)) *
                    Long.valueOf(Math.abs((z.upperEndpoint() - z.lowerEndpoint()) + 1)));

            return isOn ? value : -1 * value;
        }
    }
}