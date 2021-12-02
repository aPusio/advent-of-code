package com.pusio.day2.part2;

public class Position {
    private int horizontalPosition = 0;
    private int depth = 0;
    private int aim = 0;

    public void forward(int value){
        horizontalPosition += value;
        depth += value * aim;
    }

    public void up(int value){
        aim -= value;
    }

    public void down(int value){
        aim += value;
    }

    public Integer calculateFinalPosition() {
        return horizontalPosition * depth;
    }
}
