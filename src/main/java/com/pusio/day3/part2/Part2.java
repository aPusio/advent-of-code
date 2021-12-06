package com.pusio.day3.part2;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * https://adventofcode.com/2021/day/3
 */
public class Part2 {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/day3.input");
        List<String> contents = FileUtils.readLines(file, "UTF-8");
        String oxygen = calculateLifeSupportRating(contents, Part2::getMostCommon);
        String co2 = calculateLifeSupportRating(contents, Part2::getLessCommon);
        ;
        System.out.println("oxygen: " + oxygen);
        System.out.println("co2: " + co2);
        int oxygenValue = Integer.parseInt(oxygen, 2);
        int co2Value = Integer.parseInt(co2, 2);
        System.out.println("result: " + oxygenValue * co2Value);
    }

    private static String calculateLifeSupportRating(List<String> inputContents, BiFunction<Map<Integer, String>, Integer, String> bitCriteria) {
        List<String> contents = copyOf(inputContents);
        String result = "";
        for (int index = 0; contents.size() > 1; index++) {
            Map<Integer, String> bits = calculateBitsMap(contents);
            String bitCriteriaResult = bitCriteria.apply(bits, index);
            int finalIndex = index;
            List<String> collect = contents.stream()
                    .filter(a -> Character.toString(a.charAt(finalIndex)).equals(bitCriteriaResult))
                    .collect(Collectors.toList());
            if (collect.size() == 1) {
                result = collect.get(0);
            }
            contents = collect;
        }
        return result;
    }

    private static List<String> copyOf(List<String> contents) {
        List<String> copy = new ArrayList<>(contents);
        Collections.copy(copy, contents);
        return copy;
    }

    private static Map<Integer, String> calculateBitsMap(List<String> copy) {
        Map<Integer, String> bits = new HashMap<>();
        for (String content : copy) {
            for (int i = 0; i < content.length(); i++) {
                String storedBit = bits.getOrDefault(i, "");
                storedBit += content.charAt(i);
                bits.put(i, storedBit);
            }
        }
        return bits;
    }

    private static String getMostCommon(Map<Integer, String> bits, int index) {
        String result;
        String value = bits.get(index);
        int zeroOccurrences = StringUtils.countMatches(value, "0");
        int oneOccurrences = StringUtils.countMatches(value, "1");
        if (zeroOccurrences > oneOccurrences) {
            result = "0";
        } else {
            result = "1";
        }
        return result;
    }

    private static String getLessCommon(Map<Integer, String> bits, int index) {
        String result;
        String value = bits.get(index);
        int zeroOccurrences = StringUtils.countMatches(value, "0");
        int oneOccurrences = StringUtils.countMatches(value, "1");
        if (zeroOccurrences > oneOccurrences) {
            result = "1";
        } else {
            result = "0";
        }
        return result;
    }
}
