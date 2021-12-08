package com.pusio.day8.part1;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ProblemSolver {
    @SneakyThrows
    public Long solve(String filePath) {
        BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
        String line;
        List<String> signals = new ArrayList<>();
        List<String> display = new ArrayList<>();

        while ((line = fileReader.readLine()) != null) {
            parseLine(line, signals, display);
        }
        return display.stream()
                .map(String::length)
                .filter(length ->
                        length == 2 ||
                                length == 4 ||
                                length == 3 ||
                                length == 7)
                .count();
    }

    private void parseLine(String line, List<String> signals, List<String> display) {
        Scanner scanner = new Scanner(line);
        IntStream.range(0, 10).forEach(a -> signals.add(scanner.next()));
        scanner.next(); //delimiter
        while (scanner.hasNext()) {
            display.add((scanner.next()));
        }
    }
}
