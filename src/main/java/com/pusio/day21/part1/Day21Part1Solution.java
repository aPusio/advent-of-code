package com.pusio.day21.part1;

import com.pusio.utils.Utils;
import lombok.*;

import java.util.List;

public class Day21Part1Solution {
    private Player player1 = new Player();
    private Player player2 = new Player();
    private Integer lastRolls = 0;
    private final static int WINNING_SCORE = 1000;

    public Long solve(String filePath) {
        List<String> lines = Utils.readLines(filePath);
        player1.setScore(0);
        player1.setPosition(Integer.valueOf(String.valueOf(lines.get(0).charAt(lines.get(0).length() - 1))));
        player1.setName("player1");
        player2.setScore(0);
        player2.setPosition(Integer.valueOf(String.valueOf(lines.get(1).charAt(lines.get(1).length() - 1))));
        player2.setName("player2");

        for (int turn = 0; player1.getScore() < WINNING_SCORE && player2.getScore() < WINNING_SCORE; turn++) {
            //stupid but prints as on webstire :D
            lastRolls += 1;
            int firstRoll = lastRolls;
            lastRolls += 1;
            int secondRoll = lastRolls;
            lastRolls += 1;
            int thirdRoll = lastRolls;

            int sumOdRolls = firstRoll + secondRoll + thirdRoll;
            Player currentPlayer = turn % 2 == 0 ? player1 : player2;
            int positionAndRolls = currentPlayer.getPosition() + sumOdRolls;
            if (positionAndRolls >= 10) {
                positionAndRolls = positionAndRolls % 10;
                if (positionAndRolls == 0) {
                    positionAndRolls = 10;
                }
                currentPlayer.setPosition(positionAndRolls);
                currentPlayer.setScore(currentPlayer.getScore() + positionAndRolls);

            } else {
                currentPlayer.setPosition(positionAndRolls);
            }
            System.out.println(firstRoll + "+" + secondRoll + "+" + thirdRoll + " " + currentPlayer);
        }
        Player loser = player1.getScore() < player2.getScore() ? player1 : player2;

        return Long.valueOf(loser.getScore() * lastRolls);
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @EqualsAndHashCode
    private class Player {
        private int score;
        private int position;
        private String name;
    }
}