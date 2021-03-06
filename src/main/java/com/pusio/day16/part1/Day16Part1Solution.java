package com.pusio.day16.part1;

import com.google.common.base.Splitter;
import com.pusio.utils.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;


public class Day16Part1Solution {
    private List<Integer> parsedNumbers = new ArrayList<>();
    private Long sumOfVersions = 0L;

    public Long solve(String filePath) {
        String hexLine = Utils.readLines(filePath).stream().findFirst().orElseThrow();
        Queue<String> binaryList = Splitter.fixedLength(1).splitToList(hexLine)
                .stream().map(this::hexToBin)
                .flatMap(a -> Splitter.fixedLength(1).splitToStream(a))
                .collect(Collectors.toCollection(LinkedList::new));

        readPacket(binaryList);
        System.out.println("parsedNumbers: " + parsedNumbers);
        return sumOfVersions;
    }

    private void readPacket(Queue<String> binaries) {
        System.out.println("INPUT: " + binaries);
        String packageVersion = take(binaries, 3);
        System.out.println("packageVersion: " + packageVersion);
        sumOfVersions += binToDecLong(packageVersion);

        String packetType = take(binaries, 3);
        System.out.println("packetType: " + packetType);

        if (packetType.equals("100")) {
            String hexNumber = extractNumber(binaries);
//            parsedNumbers.add(binToDec(hexNumber));
        } else {
            //operator
            String indicator = binaries.poll();
            System.out.println("indicator: " + indicator);
            if (indicator.equals("0")) {
                String binaryLengh = take(binaries, 15);
                System.out.println("length: " + binaryLengh);
                Integer length = binToDec(binaryLengh);

                int sizeToStop = binaries.size() - length;
                while (binaries.size() > sizeToStop) {
                    readPacket(binaries);
                }
            } else {
                String binaryAmount = take(binaries, 11);
                System.out.println("binary amount: " + binaryAmount);
                Integer amount = binToDec(binaryAmount);
                for (int i = 0; i < amount; i++) {
                    readPacket(binaries);
                }

            }
        }
    }

    private String take(Queue<String> binaryList, int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(binaryList.poll());
        }
        return sb.toString();
    }

    private String extractNumber(Queue<String> binaries) {
        System.out.println("Extracting number");
        StringBuilder sb = new StringBuilder();
        String isLastIndicator = binaries.poll();
        System.out.println("isLast: " + isLastIndicator);
        while (!isLastIndicator.equals("0")) {
            String number = take(binaries, 4);
            System.out.println("number: " + number);
            sb.append(number);
            isLastIndicator = binaries.poll();
            System.out.println("isLast: " + isLastIndicator);
        }
        String number = take(binaries, 4);
        sb.append(number);
        System.out.println("number: " + number);
        System.out.println(sb);
        return sb.toString();
    }


    private String hexToBin(String hex) {
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("A", "1010");
        hex = hex.replaceAll("B", "1011");
        hex = hex.replaceAll("C", "1100");
        hex = hex.replaceAll("D", "1101");
        hex = hex.replaceAll("E", "1110");
        hex = hex.replaceAll("F", "1111");
        return hex;
    }

    private Integer binToDec(String hex) {
        return Integer.parseInt(hex, 2);
    }

    private Long binToDecLong(String hex) {
        return Long.parseLong(hex, 2);
    }

}
