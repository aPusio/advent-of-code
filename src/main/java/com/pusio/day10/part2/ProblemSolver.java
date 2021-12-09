package com.pusio.day10.part2;

import com.pusio.utils.Utils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProblemSolver {
    public Long solve(String filePath) {
        List<Integer> crabs = Utils.readUsingDelimiters(filePath, ",")
                .stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        Integer min = Collections.min(crabs);
        Integer max = Collections.max(crabs);

        return IntStream.range(min, max)
                .mapToLong(position -> fetFuel(crabs, position))
                .min()
                .orElse(0);
    }

    private Long fetFuel(List<Integer> crabs, Integer position) {
        Long fuel = 0L;
        for (Integer crab : crabs) {
            int abs = Math.abs(crab - position);
            fuel += IntStream.range(0, abs + 1)
                    .sum();
        }
        return fuel;
    }
}
