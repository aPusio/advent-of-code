package com.pusio.day14.part2betterbutstilltbd;

import com.google.common.base.Splitter;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.pusio.utils.Utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14Part2Solution {
    private String polymerTemplate = "";
    private String last = "";

    private HashMap<String, String> graph = new HashMap<>();

    public BigDecimal solve(String filePath) {
        parseInput(filePath);
        System.out.println(polymerTemplate);
        System.out.println(graph);
        last = "" + polymerTemplate.charAt(polymerTemplate.length() - 1);

        Table<String, String, BigDecimal> table = initTable();
        System.out.println("PARSED TABLE: " + table);
        for (int i = 1; i <= 40; i++) {
            table = doStep(table);
            System.out.println("STEP " + i + ":");
            System.out.println(table);
        }
        Map<String, BigDecimal> occurences = new HashMap<>();
        for (String rowKey : table.rowKeySet()) {
            BigDecimal sum = BigDecimal.ZERO;
            for (BigDecimal value : table.row(rowKey).values()) {
                sum = sum.add(value);
            }

            occurences.put(rowKey, sum);
        }
        occurences.put(last, occurences.get(last).add(new BigDecimal(1L)));

        System.out.println("Occurences: " + occurences);
        BigDecimal max = occurences.values().stream().findFirst().orElseThrow();
        BigDecimal min = occurences.values().stream().findFirst().orElseThrow();

        for (BigDecimal value : occurences.values()) {
            if (max.compareTo(value) < 0) {
                max = value;
            }
            if (min.compareTo(value) > 0) {
                min = value;
            }
        }
        return max.subtract(min);
    }

    private Table<String, String, BigDecimal> doStep(Table<String, String, BigDecimal> inputTable) {
        Table<String, String, BigDecimal> result = HashBasedTable.create();
        for (Table.Cell<String, String, BigDecimal> cell : inputTable.cellSet()) {
            BigDecimal value = cell.getValue();
            String left = cell.getRowKey();
            String right = cell.getColumnKey();
            String middle = graph.get(left + right);
            addToTable(result, left, middle, value);
            addToTable(result, middle, right, value);
        }
        return result;
    }

    private Table<String, String, BigDecimal> initTable() {
        Table<String, String, BigDecimal> table = HashBasedTable.create();
        List<String> strings = Splitter.fixedLength(1).splitToList(polymerTemplate);
        for (int i = 1; i < strings.size(); i++) {
            table.put(strings.get(i - 1), strings.get(i), new BigDecimal(1L));
        }
        return table;
    }

    public void addToTable(Table<String, String, BigDecimal> table, String row, String column, BigDecimal value) {
        BigDecimal aBigDecimal = table.get(row, column);
        if (aBigDecimal == null) {
            aBigDecimal = BigDecimal.ZERO;
        }
        table.put(row, column, aBigDecimal.add(value));
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
