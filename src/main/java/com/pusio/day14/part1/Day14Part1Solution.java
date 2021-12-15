package com.pusio.day14.part1;

import com.google.common.base.Splitter;
import com.pusio.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day14Part1Solution {
    private String polymerTemplate = "";
    private HashMap<String, String> graph = new HashMap<>();

    public Long solve(String filePath) {
        parseInput(filePath);
        System.out.println(polymerTemplate);
        System.out.println(graph);


        IntStream.range(0, 10)
                .forEach(a -> {
                            String process = process(polymerTemplate);
                            polymerTemplate = process;
                        }
                );
        Map<String, Long> occurences =
                Splitter.fixedLength(1)
                        .splitToList(polymerTemplate)
                        .stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        long max = occurences.values().stream().mapToLong(a -> a).max().orElseThrow();
        long min = occurences.values().stream().mapToLong(a -> a).min().orElseThrow();

        return max - min;
    }

    private String process(String polymerTemplate) {
        StringBuilder stringBuilder = new StringBuilder(polymerTemplate.length() + polymerTemplate.length() / 2 + 1);
        for (int i = 1; i < polymerTemplate.length(); i++) {
            String key = polymerTemplate.substring(i - 1, i + 1);
            String charToAdd = graph.get(key);
            if (i == 1) {
                stringBuilder.append(polymerTemplate.substring(i - 1, i));
            }
            stringBuilder.append(charToAdd);
            stringBuilder.append(polymerTemplate.substring(i, i + 1));
        }
        return stringBuilder.toString();
    }

    private void parseInput(String filePath) {
        List<String> strings = Utils.readLines(filePath);
        for (String line : strings) {
            if (line.contains("->")) {
                String[] split = line.split(" -> ");
                graph.put(split[0], split[1]);
            } else if (!line.isEmpty()) {
                polymerTemplate = line;
            }
        }
    }
}
