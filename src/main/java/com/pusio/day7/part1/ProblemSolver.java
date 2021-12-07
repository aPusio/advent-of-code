package com.pusio.day7.part1;

import com.pusio.utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ProblemSolver {
    public Long solve(String filePath) throws IOException {
        List<Integer> crabs = Utils.readUsingDelimiters(filePath, ",")
                .stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        Integer middleValue = crabs.get(crabs.size() / 2);

        Long fuel = 0L;
        for (Integer crab : crabs) {
            fuel += Math.abs(crab - middleValue);
        }
        return fuel;
    }
}
