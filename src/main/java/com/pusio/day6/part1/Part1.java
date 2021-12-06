package com.pusio.day6.part1;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * https://adventofcode.com/2021/day/6
 */
public class Part1 {

    public static final int MAX_DAYS = 80;

    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/day6.input");
        List<String> contents = FileUtils.readLines(file, "UTF-8");
        List<Short> fishes = parseInput(contents);

        List<Short> nextDayFishes = new ArrayList<>();
        List<Short> additionalFishes = new ArrayList<>();
       for(int day = 1; day<= MAX_DAYS; day++) {
           for (Short fish : fishes) {
               Integer nextDayFish = fish - 1;
               if (nextDayFish < 0) {
                   nextDayFish = 6;
                   additionalFishes.add((short) 8);
               }
               nextDayFishes.add(nextDayFish.shortValue());
           }
           fishes.clear();
           fishes.addAll(nextDayFishes);
           fishes.addAll(additionalFishes);
           nextDayFishes.clear();
           additionalFishes.clear();
           System.out.println("DAY: " + day);
           System.out.println("SIZE: " + fishes.size());
       }
    }

    private static List<Short> parseInput(List<String> contents) {
        return contents.stream().map(a -> a.split(","))
                .map(Arrays::asList)
                .flatMap(Collection::stream)
                .map(Short::valueOf)
                .collect(Collectors.toList());
    }
}
