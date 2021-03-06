package com.pusio.day4.part1;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * https://adventofcode.com/2021/day/4
 */
public class Part1 {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/day4.input");
        List<String> contents = FileUtils.readLines(file, "UTF-8");
        List<String> winningNumbers = parseWinningNumbers(contents);
        List<ImmutableTable<Integer, Integer, String>> boards = parseBoards(contents);
        List<Table<Integer, Integer, Boolean>> marked = createMarkedBoard(contents);
        int winningTableNumber = -1;
        for (String winningNumber : winningNumbers) {
            System.out.println("checking number" + winningNumber);
            markWinningNumbers(boards, marked, winningNumber);

            winningTableNumber = findWinningTable(marked);
            if (winningTableNumber > -1) {
                break;
            }
        }


        AtomicInteger sumOfUnmarked = new AtomicInteger();
        ImmutableTable<Integer, Integer, String> winningBoard = boards.get(winningTableNumber);
        Table<Integer, Integer, Boolean> winningMarkedBoard = marked.get(winningTableNumber);
        winningMarkedBoard.cellSet()
                .stream().filter(cell -> cell.getValue() == false)
                .forEach(cell -> {
                    String s = winningBoard.get(cell.getRowKey(), cell.getColumnKey());
                    Integer integer = Integer.valueOf(s);
                    sumOfUnmarked.addAndGet(integer);
                });
        System.out.println("sum: " + sumOfUnmarked.get());
    }


    private static int findWinningTable(List<Table<Integer, Integer, Boolean>> marked) {
        int winningTableNumber = -1;
        int boardNumber = 0;
        for (Table<Integer, Integer, Boolean> integerIntegerBooleanTable : marked) {
            for (int i = 0; i < 5; i++) {
                winningTableNumber = checkWinInColumn(winningTableNumber, boardNumber, integerIntegerBooleanTable, i);
                winningTableNumber = CheckWinInRow(winningTableNumber, boardNumber, integerIntegerBooleanTable, i);
                if (winningTableNumber > -1) {
                    return winningTableNumber;
                }
            }
            boardNumber++;
        }
        return winningTableNumber;
    }

    private static int CheckWinInRow(int winningTableNumber, int boardNumber, Table<Integer, Integer, Boolean> integerIntegerBooleanTable, int i) {
        Map<Integer, Boolean> row = integerIntegerBooleanTable.row(i);
        Optional<Boolean> firstrow = row.values().stream().filter(a -> !a)
                .findFirst();
        if (firstrow.isEmpty()) {
            System.out.println("Bingo!");
            winningTableNumber = boardNumber;
        }
        return winningTableNumber;
    }

    private static int checkWinInColumn(int winningTableNumber, int boardNumber, Table<Integer, Integer, Boolean> integerIntegerBooleanTable, int i) {
        Map<Integer, Boolean> column = integerIntegerBooleanTable.column(i);
        Optional<Boolean> firstColumn = column.values().stream().filter(a -> !a)
                .findFirst();
        if (firstColumn.isEmpty()) {
            System.out.println("Bingo!");
            winningTableNumber = boardNumber;
        }
        return winningTableNumber;
    }

    private static void markWinningNumbers(List<ImmutableTable<Integer, Integer, String>> boards, List<Table<Integer, Integer, Boolean>> marked, String winningNumber) {
        for (int boardNumber = 0; boardNumber < boards.size(); boardNumber++) {
            ImmutableTable<Integer, Integer, String> board = boards.get(boardNumber);

            int finalBoardNumber = boardNumber;
            board.cellSet()
                    .stream()
                    .filter(a -> Objects.equals(a.getValue(), winningNumber))
                    .forEach(cell -> {
                        Table<Integer, Integer, Boolean> markedTable = marked.get(finalBoardNumber);
                        markedTable.put(cell.getRowKey(), cell.getColumnKey(), true);
                    });
        }
    }

    private static List<Table<Integer, Integer, Boolean>> createMarkedBoard(List<String> contents) {
        List<Table<Integer, Integer, Boolean>> marked = new ArrayList<>();
        for (int i = 0; i < contents.size() / 6; i++) {
            Table<Integer, Integer, Boolean> boardBuilder = HashBasedTable.create();
            for (int row = 0; row < 5; row++) {
                for (int column = 0; column < 5; column++) {
                    boardBuilder.put(row, column, false);
                }
            }
            marked.add(boardBuilder);
        }

        for (Table<Integer, Integer, Boolean> integerIntegerBooleanTable : marked) {
            System.out.println(marked);
        }

        return marked;
    }

    private static List<ImmutableTable<Integer, Integer, String>> parseBoards(List<String> contents) {
        List<ImmutableTable<Integer, Integer, String>> boards = new ArrayList<>();
        for (int i = 1; i < contents.size(); i += 6) {
            ImmutableTable.Builder<Integer, Integer, String> boardBuilder = ImmutableTable.<Integer, Integer, String>builder();
            for (int rowCounter = 0; rowCounter <= 5; rowCounter++) {
                String boardRow = contents.get(i + rowCounter);
                String[] s = StringUtils.split(boardRow, " ");
                List<String> parsedColumn = Arrays.asList(s);
                int columnCounter = 0;
                for (String singleElement : parsedColumn) {
                    boardBuilder.put(rowCounter - 1, columnCounter, singleElement);
                    columnCounter++;
                }

            }
            boards.add(boardBuilder.build());
        }

        for (ImmutableTable<Integer, Integer, String> board : boards) {
            System.out.println(board);
        }

        return boards;
    }

    private static List<String> parseWinningNumbers(List<String> contents) {
        String winningNumbersString = contents.get(0);
        String[] split = StringUtils.split(winningNumbersString, ",");
        List<String> winningNumbers = Arrays.asList(split);
        return winningNumbers;
    }
}
