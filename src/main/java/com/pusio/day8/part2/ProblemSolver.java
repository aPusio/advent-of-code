package com.pusio.day8.part2;

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

            Map<String, String> displayArrange = new HashMap<>();
            Map<String, String> leftNumbers = new HashMap<>();
            leftNumbers.put("1", signals.stream().filter(a -> a.length() == 2).findFirst().orElseThrow());
            leftNumbers.put("4", signals.stream().filter(a -> a.length() == 4).findFirst().orElseThrow());
            leftNumbers.put("7", signals.stream().filter(a -> a.length() == 3).findFirst().orElseThrow());
            leftNumbers.put("8", signals.stream().filter(a -> a.length() == 7).findFirst().orElseThrow());

            displayArrange.put("top", getTop(leftNumbers));
            displayArrange.put("bot", getBot2(leftNumbers, signals, displayArrange.get("top")));
            displayArrange.put("middle", getMiddle2(leftNumbers, displayArrange.get("top"), displayArrange.get("bot")));
            displayArrange.put("topleft", topLeft(leftNumbers, displayArrange.get("middle")));
            displayArrange.put("topright", getTopRight(leftNumbers, signals, displayArrange.get("topleft")));
            displayArrange.put("botleft", getBotLeft(leftNumbers, signals, displayArrange.get("topright"), displayArrange.get("middle")));
            displayArrange.put("botright", getBotRight(displayArrange));

            Map<String, String> decoder = new HashMap<>();
            decoder.put(sort(
                            displayArrange.get("top") +
                                    displayArrange.get("middle") +
                                    displayArrange.get("topleft") +
                                    displayArrange.get("bot") +
                                    displayArrange.get("topright") +
                                    displayArrange.get("botright")),
                    "9");
            decoder.put(sort(
                            displayArrange.get("top") +
                                    displayArrange.get("middle") +
                                    displayArrange.get("topleft") +
                                    displayArrange.get("bot") +
                                    displayArrange.get("topright") +
                                    displayArrange.get("botleft") +
                                    displayArrange.get("botright")),
                    "8");

            decoder.put(sort(
                            displayArrange.get("top") +
                                    displayArrange.get("topright") +
                                    displayArrange.get("botright")),
                    "7");
            decoder.put(sort(
                            displayArrange.get("top") +
                                    displayArrange.get("middle") +
                                    displayArrange.get("topleft") +
                                    displayArrange.get("bot") +
                                    displayArrange.get("botleft") +
                                    displayArrange.get("botright")),
                    "6");
            decoder.put(sort(
                            displayArrange.get("top") +
                                    displayArrange.get("middle") +
                                    displayArrange.get("topleft") +
                                    displayArrange.get("bot") +
                                    displayArrange.get("botright")),
                    "5");
            decoder.put(sort(
                            displayArrange.get("middle") +
                                    displayArrange.get("topleft") +
                                    displayArrange.get("topright") +
                                    displayArrange.get("botright")),
                    "4");
            decoder.put(sort(
                            displayArrange.get("top") +
                                    displayArrange.get("middle") +
                                    displayArrange.get("bot") +
                                    displayArrange.get("topright") +
                                    displayArrange.get("botright")),
                    "3");
            decoder.put(sort(
                            displayArrange.get("top") +
                                    displayArrange.get("middle") +
                                    displayArrange.get("bot") +
                                    displayArrange.get("topright") +
                                    displayArrange.get("botleft")),
                    "2");
            decoder.put(sort(
                            displayArrange.get("topright") +
                                    displayArrange.get("botright")),
                    "1");
            decoder.put(sort(
                            displayArrange.get("top") +
                                    displayArrange.get("topleft") +
                                    displayArrange.get("bot") +
                                    displayArrange.get("topright") +
                                    displayArrange.get("botleft") +
                                    displayArrange.get("botright")),
                    "0");
            System.out.println(displayArrange);
            System.out.println(decoder);

            String collectedNumber = display.stream()
                    .map(this::sort)
                    .map(decoder::get)
                    .collect(Collectors.joining());
            System.out.println(collectedNumber);

            results.add(Integer.valueOf(collectedNumber));
        }
        return results.stream().mapToLong(a -> a).sum();
    }

    private String getMiddle2(Map<String, String> leftNumbers, String top, String bot) {
        List<String> threeList = getStringList(leftNumbers.get("3"));
        getStringList(leftNumbers.get("1")).forEach(
                threeList::remove);
        threeList.remove(top);
        threeList.remove(bot);

        return threeList.stream()
                .findFirst()
                .orElseThrow();
    }

    private String getBot2(Map<String, String> leftNumbers, List<String> signals, String top) {
        List<String> length5 = signals.stream().filter(a -> a.length() == 5).collect(Collectors.toList());
        String three = length5.stream()
                .filter(word ->
                        getStringList(word).containsAll(getStringList(leftNumbers.get("1")))
                )
                .findFirst().orElseThrow();
        leftNumbers.put("3", three);

        List<String> threeList = getStringList(leftNumbers.get("3"));
        getStringList(leftNumbers.get("4")).forEach(
                threeList::remove);
        threeList.remove(top);
        return threeList.stream()
                .findFirst()
                .orElseThrow();
    }

    private String getTop(Map<String, String> leftNumbers) {
        List<String> one = getStringList(leftNumbers.get("1"));
        List<String> seven = getStringList(leftNumbers.get("7"));
        one.forEach(seven::remove);
        return seven.stream().findFirst().orElseThrow();
    }

    private List<String> getStringList(String s) {
        List<String> one = s
                .chars()
                .mapToObj(a -> (char) a)
                .map(String::valueOf)
                .collect(Collectors.toList());
        return one;
    }

    private String topLeft(Map<String, String> leftNumbers, String middle) {
        List<String> fourList = getStringList(leftNumbers.get("4"));
        List<String> oneList = getStringList(leftNumbers.get("1"));
        oneList.forEach(fourList::remove);
        fourList.remove(middle);
        return fourList.stream().findFirst().orElseThrow();
    }

    private String getTopRight(Map<String, String> leftNumbers, List<String> signals, String topleft) {
        List<String> length5 = signals.stream().filter(a -> a.length() == 5).collect(Collectors.toList());
        String five = length5.stream()
                .filter(word ->
                        getStringList(word).contains(topleft)
                )
                .findFirst().orElseThrow();
        leftNumbers.put("5", five);
        List<String> eightList = getStringList(leftNumbers.get("4"));
        getStringList(leftNumbers.get("5")).forEach(
                eightList::remove);
        return eightList.stream()
                .findFirst()
                .orElseThrow();
    }

    private String getBotLeft(Map<String, String> leftNumbers, List<String> signals, String topright, String middle) {
        List<String> length6 = signals.stream().filter(a -> a.length() == 6).collect(Collectors.toList());
        String nine = length6.stream()
                .filter(word ->
                        getStringList(word).contains(topright) && getStringList(word).contains(middle)
                )
                .findFirst().orElseThrow();
        leftNumbers.put("9", nine);

        List<String> eightList = getStringList(leftNumbers.get("8"));
        getStringList(leftNumbers.get("9")).forEach(
                eightList::remove);
        return eightList.stream()
                .findFirst()
                .orElseThrow();
    }

    private String getBotRight(Map<String, String> displayArrange) {
        List<String> stringList = getStringList("abcdefg");
        displayArrange.values().forEach(stringList::remove);
        return stringList.stream()
                .findFirst()
                .orElseThrow();
    }

    private String sort(String test) {
        char[] ar = test.toCharArray();
        Arrays.sort(ar);
        return String.valueOf(ar);
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

}
