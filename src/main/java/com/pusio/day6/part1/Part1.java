package com.pusio.day6.part1;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * https://adventofcode.com/2021/day/6
 */
public class Part1 {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/day1.input");
        List<String> contents = FileUtils.readLines(file, "UTF-8");
    }
}
