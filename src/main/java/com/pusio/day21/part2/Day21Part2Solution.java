package com.pusio.day21.part2;

import com.pusio.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;

public class Day21Part2Solution {
    private final static int WINNING_SCORE = 21;

    public Long solve(String filePath) {
        Pair<Integer, Integer> startPosition = initializeFirstUniverse(filePath);

        long[][][][] universes = new long[22][10][22][10];
        universes[0][startPosition.getLeft()][0][startPosition.getRight()] = 1;

        range(0, WINNING_SCORE).forEach(p1Score -> {
            range(0, WINNING_SCORE).forEach(p2Score -> {
                range(0, 10).forEach(p1Position -> {
                    range(0, 10).forEach(p2Position -> {
                        rangeClosed(1, 3).forEach(roll1 -> {
                            rangeClosed(1, 3).forEach(roll2 -> {
                                rangeClosed(1, 3).forEach(roll3 -> {
                                    int positionAndRolls1 = getPositionAndRolls(p1Position, roll1, roll2, roll3);
                                    int min1 = Math.min(p1Score + positionAndRolls1 + 1, WINNING_SCORE);
                                    if (p1Score + positionAndRolls1 + 1 >= WINNING_SCORE) {
                                        universes[min1][positionAndRolls1][p1Score][p2Position] += universes[p1Score][p1Position][p2Score][p2Position];
                                    } else {
                                        rangeClosed(1, 3).forEach(roll4 -> {
                                            rangeClosed(1, 3).forEach(roll5 -> {
                                                rangeClosed(1, 3).forEach(roll6 -> {
                                                    int positionAndRolls2 = getPositionAndRolls(p2Position, roll4, roll5, roll6);
                                                    int min2 = Math.min(p2Score + positionAndRolls2 + 1, WINNING_SCORE);
                                                    universes[min1][positionAndRolls1][min2][positionAndRolls2] += universes[p1Score][p1Position][p2Score][p2Position];
                                                });
                                            });
                                        });
                                    }
                                });
                            });
                        });
                    });
                });
            });
        });

        Long p1Wins = 0L;
        Long p2Wins = 0L;

        range(0, 10).forEach(p1Position -> {
            range(0, 10).forEach(p2Position -> {
                range(0, 21).forEach(score -> {

                });
            });
        });
        for (int p1Position = 0; p1Position < 10; p1Position++) {
            for (int p2Position = 0; p2Position < 10; p2Position++) {
                for (int score = 0; score < WINNING_SCORE; score++) {
                    p1Wins += universes[WINNING_SCORE][p1Position][score][p2Position];
                    p2Wins += universes[score][p1Position][WINNING_SCORE][p2Position];
                }
            }
        }

        return p1Wins > p2Wins ? p1Wins : p2Wins;
    }

    private int getPositionAndRolls(int p1Position, int roll1, int roll2, int roll3) {
        int sumOfRolls = roll1 + roll2 + roll3;
        int positionAndRolls = p1Position + sumOfRolls;
        positionAndRolls = positionAndRolls % 10;
        return positionAndRolls;
    }

    private Pair<Integer, Integer> initializeFirstUniverse(String filePath) {
        List<String> lines = Utils.readLines(filePath);
        Integer player1Position = Integer.parseInt(StringUtils.stripStart(lines.get(0), "Player 1 starting position: ")) - 1;
        Integer player2Position = Integer.parseInt(StringUtils.stripStart(lines.get(1), "Player 1 starting position: ")) - 1;
        return Pair.of(player1Position, player2Position);
    }
}