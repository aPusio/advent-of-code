package com.pusio.day8.part2refactored;

import com.google.common.base.Splitter;
import com.google.common.collect.MoreCollectors;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProblemSolver {
    @SneakyThrows
    public Long solve(String filePath) {
        BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
        String line;
        List<Integer> results = new ArrayList<>();
        while ((line = fileReader.readLine()) != null) {
            List<String> signals = new ArrayList<>();
            List<String> display = new ArrayList<>();
            parseLine(line, signals, display);
            String one = signals.stream()
                    .filter(a -> a.length() == 2)
                    .map(this::sort)
                    .collect(MoreCollectors.onlyElement());
            String four = signals.stream()
                    .filter(a -> a.length() == 4)
                    .map(this::sort)
                    .collect(MoreCollectors.onlyElement());
            String seven = signals.stream()
                    .filter(a -> a.length() == 3)
                    .map(this::sort)
                    .collect(MoreCollectors.onlyElement());
            String eight = signals.stream()
                    .filter(a -> a.length() == 7)
                    .map(this::sort)
                    .collect(MoreCollectors.onlyElement());
            String six = signals.stream()
                    .filter(a -> a.length() == 6)
                    .filter(a -> toCharList(a)
                            .stream()
                            .filter(one::contains)
                            .count() == 1)
                    .map(this::sort)
                    .collect(MoreCollectors.onlyElement());
            String zero = signals.stream()
                    .filter(a -> a.length() == 6)
                    .filter(a -> !toCharList(a).containsAll(toCharList(six)))
                    .filter(a -> !toCharList(a).containsAll(toCharList(four)))
                    .map(this::sort)
                    .collect(MoreCollectors.onlyElement());
            String nine = signals.stream()
                    .filter(a -> a.length() == 6)
                    .filter(a -> !toCharList(a).containsAll(toCharList(six)))
                    .filter(a -> !toCharList(a).containsAll(toCharList(zero)))
                    .map(this::sort)
                    .collect(MoreCollectors.onlyElement());
            String topRight = signals.stream()
                    .filter(a -> a.length() == 7)
                    .map(a -> toCharList(a)
                            .stream()
                            .filter(singleChar -> !six.contains(singleChar))
                            .collect(MoreCollectors.onlyElement()))
                    .map(this::sort)
                    .collect(MoreCollectors.onlyElement());
            String five = signals.stream()
                    .filter(a -> a.length() == 5)
                    .filter(a -> !a.contains(topRight))
                    .map(this::sort)
                    .collect(MoreCollectors.onlyElement());
            String bottomLeft = signals.stream()
                    .filter(a -> a.length() == 7)
                    .map(a -> toCharList(a)
                            .stream()
                            .filter(singleChar -> !nine.contains(singleChar))
                            .collect(MoreCollectors.onlyElement()))
                    .map(this::sort)
                    .collect(MoreCollectors.onlyElement());
            String three = signals.stream()
                    .filter(a -> a.length() == 5)
                    .filter(a -> !toCharList(a).containsAll(toCharList(five)))
                    .filter(a -> !a.contains(bottomLeft))
                    .map(this::sort)
                    .collect(MoreCollectors.onlyElement());
            String two = signals.stream()
                    .filter(a -> a.length() == 5)
                    .filter(a -> !toCharList(a).containsAll(toCharList(five)))
                    .filter(a -> !toCharList(a).containsAll(toCharList(three)))
                    .map(this::sort)
                    .collect(MoreCollectors.onlyElement());

            Map<String, String> decoder = new HashMap<>();
            decoder.put(zero, "0");
            decoder.put(one, "1");
            decoder.put(two, "2");
            decoder.put(three, "3");
            decoder.put(four, "4");
            decoder.put(five, "5");
            decoder.put(six, "6");
            decoder.put(seven, "7");
            decoder.put(eight, "8");
            decoder.put(nine, "9");

            System.out.println(decoder);

            String collectedNumber = display.stream()
                    .map(this::sort)
                    .map(decoder::get)
                    .collect(Collectors.joining());

            System.out.println(collectedNumber);

            results.add(Integer.valueOf(collectedNumber));
        }
        return results.stream()
                .mapToLong(a -> a)
                .sum();
    }

    private List<String> toCharList(String a) {
        return Splitter.fixedLength(1).splitToList(a);
    }

    private void parseLine(String line, List<String> signals, List<String> display) {
        Scanner scanner = new Scanner(line);
        IntStream.range(0, 10).forEach(a -> signals.add(scanner.next()));
        System.out.println(signals);
        scanner.next(); //delimiter
        while (scanner.hasNext()) {
            display.add((scanner.next()));
        }
    }

    private String sort(String test) {
        char[] ar = test.toCharArray();
        Arrays.sort(ar);
        return String.valueOf(ar);
    }
}
