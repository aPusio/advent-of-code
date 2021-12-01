package com.pusio.day1;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 */
public class Part2 {
    public static void main(String[] args) throws IOException {
        int counter = 0;
        int previousSumOfMeasurements = Integer.MAX_VALUE;
        File file = new File("src/main/resources/day1.input");
        List<String> contents = FileUtils.readLines(file, "UTF-8");
        for (int i = 2; i < contents.size(); i++) {
            int first = Integer.parseInt(contents.get(i));
            int second = Integer.parseInt(contents.get(i - 1));
            int third = Integer.parseInt(contents.get(i - 2));
            int sumOfMeasurements = first + second + third;

            if (sumOfMeasurements > previousSumOfMeasurements) {
                counter++;
            }
            previousSumOfMeasurements = sumOfMeasurements;
        }
        System.out.println(counter);
    }
}
