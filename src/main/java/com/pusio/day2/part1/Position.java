package com.pusio.day2.part1;

public class Position {
    private int horizontalPosition = 0;
    private int depth = 0;

    public void forward(int value) {
        horizontalPosition += value;
    }

    public void up(int value) {
        depth -= value;
    }

    public void down(int value) {
        depth += value;
    }

    public Integer calculateFinalPosition() {
        return horizontalPosition * depth;
    }
}
