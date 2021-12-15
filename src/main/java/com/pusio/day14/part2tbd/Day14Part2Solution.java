package com.pusio.day14.part2tbd;

import com.google.common.base.Splitter;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.pusio.utils.Utils;

import java.util.*;

public class Day14Part2Solution {
    private String polymerTemplate = "";
    private String first = "";
    private String last = "";

    private HashMap<String, String> graph = new HashMap<>();

    public Long solve(String filePath) {
        parseInput(filePath);
        System.out.println(polymerTemplate);
        System.out.println(graph);
        List<String> list = inputToMap();
        first = "" + polymerTemplate.charAt(0);
        last = "" + polymerTemplate.charAt(polymerTemplate.length() - 1);

        Table<String, String, Long> table = initTable(list);
        System.out.println("PARSED TABLE: " + table);
        for (int i = 1; i <= 40; i++) {
            table = process2(table);
            System.out.println("STEP " + i + ":");
            System.out.println(table);
        }

        Map<String, Long> occurences = new HashMap<>();
        Set<Table.Cell<String, String, Long>> cells = table.cellSet();
        for (Table.Cell<String, String, Long> cell : cells) {
            Long valueFromTable = cell.getValue();

            String s2 = "" + cell.getRowKey().charAt(1);
            Long s2Val = occurences.getOrDefault(s2, 0L);
            occurences.put(s2, valueFromTable + s2Val);
        }
        occurences.put(first, occurences.get(first) + 1L);
        occurences.put(last, occurences.get(last) + 1L);

        System.out.println("Occurences: " + occurences);
        long max = occurences.values().stream().mapToLong(a -> a).max().orElseThrow();
        long min = occurences.values().stream().mapToLong(a -> a).min().orElseThrow();

        return max - min;
    }

    private Table<String, String, Long> initTable(List<String> list) {
        Table<String, String, Long> table = HashBasedTable.create();
        for (int i = 1; i < list.size(); i++) {
            String from = list.get(i - 1);
            String to = list.get(i);
            table.put(from, to, 1L);
        }
        return table;
    }

    private void addToTable(Table<String, String, Long> table, List<String> list, Long amount) {
        for (int i = 1; i < list.size(); i++) {
            String from = list.get(i - 1);
            String to = list.get(i);
            Long valueFromTable = table.get(from, to);
            if (valueFromTable == null) {
                table.put(from, to, amount);
            } else {
                table.put(from, to, valueFromTable + amount);
            }
        }
    }

    private List<String> inputToMap() {
        List<String> splitted = Splitter.fixedLength(1).splitToList(polymerTemplate);
        List<String> list = new ArrayList<>();
        String previous = "";
        for (String s : splitted) {
            if (previous.isEmpty()) {
                previous = s;
            } else {
                list.add(previous + s);
                previous = s;
            }
        }
        return list;
    }

    private Table<String, String, Long> process2(Table<String, String, Long> inputTable) {
        boolean isFirst = true;
        Table<String, String, Long> resultTable = HashBasedTable.create();
        Set<Table.Cell<String, String, Long>> cells = inputTable.cellSet();
        for (Table.Cell<String, String, Long> cell : cells) {
            List<String> process = process(List.of(cell.getRowKey(), cell.getColumnKey()));
            if (isFirst) {
                isFirst = false;
                System.out.println("add: " + process + " " + 1L);
                addToTable(resultTable, process, 1L);
                if ((cell.getValue() - 1) > 0) {
                    process.remove(0);
                    addToTable(resultTable, process, (cell.getValue() - 1));
                }
            } else {
                process.remove(0);
                System.out.println("add: " + process + " " + cell.getValue());
                addToTable(resultTable, process, cell.getValue());
            }
        }


        return resultTable;
    }

    private List<String> process(List<String> list) {
        List<String> result = new ArrayList<>(list.size() * 2);
        String previous = "";
        for (String key : list) {
            String newChar = graph.get(key);
            if (previous.isEmpty()) {
                result.add(key.substring(0, 1) + newChar);
                result.add(newChar + key.substring(1, 2));
            } else {
                result.add(previous + newChar);
                result.add(newChar + key.substring(1, 2));
            }
            previous = key.substring(1, 2);
        }
        return result;
    }

    private void parseInput(String filePath) {
        List<String> strings = Utils.readLines(filePath);
        for (String line : strings) {
            if (line.contains("->")) {
                String[] split = line.split(" -> ");
                graph.put(split[0], split[1]);
            } else if (!line.isEmpty()) {
                polymerTemplate = line;
            }
        }
    }
}
