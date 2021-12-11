package com.pusio.day10.part1;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.pusio.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class ProblemSolver {
    private Map<String, String> syntaxMap = ImmutableMap.of(
            "(", ")",
            "{", "}",
            "[", "]",
            "<", ">");

    public Long solve(String filePath) throws IOException {
        List<List<String>> splitLines = parseInput(filePath);
        List<String> result = new ArrayList<>();

        for (List<String> splitLine : splitLines) {
            String firstIncorrect = "";
            Stack<String> stack = new Stack<>();
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
            if (!stack.isEmpty()) {
                result.add(firstIncorrect);
            }
        }

        return result.stream()
                .mapToLong(this::getPoints)
                .sum();
    }

    private List<List<String>> parseInput(String filePath) {
        return Utils.readLines(filePath).stream()
                .map(a -> Splitter.fixedLength(1).splitToList(a))
                .collect(Collectors.toList());
    }

    private Integer getPoints(String a) {
        //): 3 points.
        //]: 57 points.
        //}: 1197 points.
        //>: 25137 points.
        if (a.equals(")")) {
            return 3;
        }
        if (a.equals("]")) {
            return 57;
        }
        if (a.equals("}")) {
            return 1197;
        }
        if (a.equals(">")) {
            return 25137;
        }
        return 0;
    }

    private boolean isCorrect(String open, String close) {
        return syntaxMap.getOrDefault(open, "").equals(close);
    }
}
