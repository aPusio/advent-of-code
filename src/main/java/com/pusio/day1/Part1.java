package com.pusio.day1;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 */
public class Part1 {
    public static void main(String[] args) throws IOException {
        int counter = 0;

        File file = new File("src/main/resources/day1.input");
        List<String> contents = FileUtils.readLines(file, "UTF-8");
        for (int i = 1; i < contents.size(); i++) {
            int current = Integer.parseInt(contents.get(i));
            int previous = Integer.parseInt(contents.get(i - 1));

            if (current > previous) {
                counter++;
            }
        }
        System.out.println(counter);
    }
}
