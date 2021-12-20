package com.pusio.day20.part2;

import com.pusio.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day20Part2Solution {
    private String imageEnchantmentAlgo;
    private Map<Point, Character> baseImage = new HashMap<>();

    public Long solve(String filePath) {
        List<String> lines = initEnchantmentAlgorithm(filePath);
        initImage(lines);
        IntStream.range(0, 50).forEach(counter -> {
            int minY = baseImage.keySet().stream().map(Point::getY).mapToInt(a -> a).min().orElseThrow();
            int minX = baseImage.keySet().stream().map(Point::getX).mapToInt(a -> a).min().orElseThrow();
            Character defaultChar;
            if (imageEnchantmentAlgo.charAt(0) == '.') {
                defaultChar = imageEnchantmentAlgo.charAt(0);
            } else {
                defaultChar = baseImage.get(new Point(minY, minX));
            }
            appendFrameWithZeros(defaultChar);
            Map<Point, Character> newBaseImage = new HashMap<>();
            for (Point point : baseImage.keySet()) {
                String binaryNumber = point.getSurroundingsAndMe()
                        .stream()
                        .map(p -> baseImage.getOrDefault(p, defaultChar))
                        .map(singleChar -> singleChar == '#' ? "1" : "0")
                        .collect(Collectors.joining());
                int index = Integer.parseInt(binaryNumber, 2);
                char charFromAlgorithm = imageEnchantmentAlgo.charAt(index);
                newBaseImage.put(point, charFromAlgorithm);
            }
            baseImage = newBaseImage;
        });

        return baseImage.values().stream().filter(val -> val == '#').count();
    }

    private void print(Map<Point, Character> newBaseImage) {
        System.out.println("---MAP---");
        int minY = newBaseImage.keySet().stream().map(Point::getY).mapToInt(a -> a).min().orElseThrow();
        int maxY = newBaseImage.keySet().stream().map(Point::getY).mapToInt(a -> a).max().orElseThrow();
        int minX = newBaseImage.keySet().stream().map(Point::getX).mapToInt(a -> a).min().orElseThrow();
        int maxX = newBaseImage.keySet().stream().map(Point::getX).mapToInt(a -> a).max().orElseThrow();

        IntStream.rangeClosed(minY, maxY).forEach(y -> {
            IntStream.rangeClosed(minX, maxX).forEach(x -> {
                System.out.print(newBaseImage.get(new Point(y, x)));
            });
            System.out.println();
        });
    }

    private void appendFrameWithZeros(char blankChar) {
        int minY = baseImage.keySet().stream().map(Point::getY).mapToInt(a -> a).min().orElseThrow() - 1;
        int maxY = baseImage.keySet().stream().map(Point::getY).mapToInt(a -> a).max().orElseThrow() + 1;
        int minX = baseImage.keySet().stream().map(Point::getX).mapToInt(a -> a).min().orElseThrow() - 1;
        int maxX = baseImage.keySet().stream().map(Point::getX).mapToInt(a -> a).max().orElseThrow() + 1;

        IntStream.rangeClosed(minY, maxY).forEach(y -> {
            baseImage.put(new Point(y, maxX), blankChar);
            baseImage.put(new Point(y, minX), blankChar);
        });
        IntStream.rangeClosed(minX, maxX).forEach(x -> {
            baseImage.put(new Point(maxY, x), blankChar);
            baseImage.put(new Point(minY, x), blankChar);
        });
    }

    private void initImage(List<String> lines) {
        List<String> image = lines.subList(2, lines.size());
        for (int y = 0; y < image.size(); y++) {
            String chars = image.get(y);
            for (int x = 0; x < chars.length(); x++) {
                baseImage.put(new Point(y, x), chars.charAt(x));
            }
        }
    }

    private List<String> initEnchantmentAlgorithm(String filePath) {
        List<String> lines = Utils.readLines(filePath);
        imageEnchantmentAlgo = lines.get(0);
        return lines;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @EqualsAndHashCode
    private class Point {
        private final int y;
        private final int x;

        public List<Point> getSurroundingsAndMe() {
            return List.of(
                    new Point(this.y - 1, this.x - 1),
                    new Point(this.y - 1, this.x),
                    new Point(this.y - 1, this.x + 1),

                    new Point(this.y, this.x - 1),
                    new Point(this.y, this.x),
                    new Point(this.y, this.x + 1),

                    new Point(this.y + 1, this.x - 1),
                    new Point(this.y + 1, this.x),
                    new Point(this.y + 1, this.x + 1)
            );
        }
    }
}