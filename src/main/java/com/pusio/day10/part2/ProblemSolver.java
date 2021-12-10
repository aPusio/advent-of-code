package com.pusio.day10.part2;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.pusio.utils.Utils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ProblemSolver {
    private final Map<String, String> syntaxMap = ImmutableMap.of(
            "(", ")",
            "{", "}",
            "[", "]",
            "<", ">");

    public Long solve(String filePath) throws IOException {
        List<List<String>> splitLines = parseInput(filePath);
        List<List<String>> result = new ArrayList<>();

        for (List<String> splitLine : splitLines) {
            Stack<String> stack = new Stack<>();
            String firstIncorrect = "";
            List<String> localResult = new ArrayList<>();
            for (String singleChar : splitLine) {
                if (syntaxMap.containsKey(singleChar)) {
                    stack.add(singleChar);
                } else {
                    String pop = stack.pop();
                    boolean correct = isCorrect(pop, singleChar);
                    if (correct == false && firstIncorrect.equals("")) {
                        firstIncorrect = singleChar;
                    }
                }
            }
            if (firstIncorrect.equals("")) {
                while (!stack.isEmpty()) {
                    localResult.add(findClosingChar(stack.pop()));
                }
                result.add(localResult);
            }
        }

        List<Long> pointsResult = result.stream()
                .filter(Objects::nonNull)
                .map(this::calculatePoints)
                .sorted()
                .collect(Collectors.toList());

        return pointsResult.get(pointsResult.size() / 2);
    }

    private List<List<String>> parseInput(String filePath) {
        return Utils.readLines(filePath).stream()
                .map(a -> Splitter.fixedLength(1).splitToList(a))
                .collect(Collectors.toList());
    }

    private Long calculatePoints(List<String> strings) {
        int multiply = 5;
        long totalScore = 0;
        for (String string : strings) {
            totalScore *= multiply;
            totalScore += getPoints(string);
        }
        return totalScore;
    }

    private String findClosingChar(String pop) {
        return syntaxMap.getOrDefault(pop, "");
    }

    private Integer getPoints(String a) {
        if (a.equals(")")) {
            return 1;
        }
        if (a.equals("]")) {
            return 2;
        }
        if (a.equals("}")) {
            return 3;
        }
        if (a.equals(">")) {
            return 4;
        }
        return 0;
    }

    private boolean isCorrect(String open, String close) {
        return syntaxMap.getOrDefault(open, "").equals(close);
    }
}
