package com.pusio.day6.part2;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * https://adventofcode.com/2021/day/6
 */
public class Part2 {

    public static final int FISH_TIME_OF_LIFE = 8;
    public static final int MAX_DAYS = 256;

    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/day6_simple.input");
        List<String> contents = FileUtils.readLines(file, "UTF-8");
        List<Integer> fishes = parseInput(contents);
        Map<Integer, Long> fishesMap = initializeMap(fishes);

        for (int day = 1; day <= MAX_DAYS; day++) {
            Map<Integer, Long> tmpMap = new HashMap<>();
            List<Integer> keys = fishesMap.keySet()
                    .stream()
                    .sorted(Collections.reverseOrder())
                    .collect(Collectors.toList());
            for (Integer key : keys) {
                if (key == 0) {
                    tmpMap.put(8, fishesMap.get(0));
                    tmpMap.put(6, tmpMap.get(6) + fishesMap.get(0));
                } else {
                    tmpMap.put(key - 1, fishesMap.get(key));
                }
            }
            fishesMap = new HashMap<>(tmpMap);
            System.out.println("DAY " + day);
            Long sum = 0L;
            for (Long value : fishesMap.values()) {
                sum += value;
            }

            sum = fishesMap.values()
                    .stream()
                    .mapToLong(Long::longValue)
                    .sum();
            System.out.println("SIZE: " + sum);
        }
    }

    private static Map<Integer, Long> initializeMap(List<Integer> fishes) {
        Map<Integer, Long> fishesMap = new HashMap<>();
        for (int i = 0; i <= FISH_TIME_OF_LIFE; i++) {
            fishesMap.put(i, 0L);
        }
        for (Integer fish : fishes) {
            fishesMap.computeIfPresent(fish, (key, val) -> val + 1L);
        }
        return fishesMap;
    }

    private static List<Integer> parseInput(List<String> contents) {
        return contents.stream().map(a -> a.split(","))
                .map(Arrays::asList)
                .flatMap(Collection::stream)
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }
}
