package com.pusio.day2.part2;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * https://adventofcode.com/2021/day/2
 */
public class Part2 {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/day2.input");
        List<String> contents = FileUtils.readLines(file, "UTF-8");
        Position position = new Position();

        contents.stream()
                .map(Part2::parseInput)
                .forEach(pair -> calculatePosition(position, pair));

        System.out.println("Position: " + position.calculateFinalPosition());
        ;
    }

    private static void calculatePosition(Position position, Pair<Moves, Integer> pair) {
        switch (pair.getLeft()) {
            case FORWARD:
                position.forward(pair.getRight());
                break;
            case UP:
                position.up(pair.getRight());
                break;
            case DOWN:
                position.down(pair.getRight());
                break;
        }
    }

    private static Pair<Moves, Integer> parseInput(String a) {
        String[] splitted = StringUtils.split(a, " ");
        Moves move = Moves.valueOf(splitted[0].toUpperCase());
        int value = Integer.parseInt(splitted[1]);
        return Pair.of(move, value);
    }

    enum Moves {
        FORWARD,
        UP,
        DOWN
    }
}
