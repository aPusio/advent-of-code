package com.pusio.day7.part1;

import java.io.File;
import java.io.IOException;

/**
 * https://adventofcode.com/2021/day/7
 */
public class Part1 {

    public static void main(String[] args) throws IOException {
        File sampleFile = new File("src/main/resources/day7_sample.input");
        File file = new File("src/main/resources/day7.input");

        System.out.println("SAMPLE RESULT: " + new ProblemSolver().solve(sampleFile));
        System.out.println("RESULT: " + new ProblemSolver().solve(file));
    }
}
