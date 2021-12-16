package com.pusio.day17.part2;

import com.google.common.base.Splitter;
import com.pusio.utils.Utils;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.Collectors;

public class Day17Part2Solution {
    //    private List<Long> parsedNumbers = new ArrayList<>();
    private Long sumOfVersions = 0L;
    private Stack<Long> stackOfResults = new Stack<>();

    public Long solve(String filePath) {
        String hexLine = Utils.readLines(filePath).stream().findFirst().orElseThrow();
        Queue<String> binaryList = Splitter.fixedLength(1).splitToList(hexLine)
                .stream().map(this::hexToBin)
                .flatMap(a -> Splitter.fixedLength(1).splitToStream(a))
                .collect(Collectors.toCollection(LinkedList::new));

        readPacket(binaryList);
        System.out.println("parsedNumbers: " + stackOfResults);
        return sumOfVersions;
    }

    private void readPacket(Queue<String> binaries) {
        //System.out.println("INPUT: " + binaries);
        String packageVersion = take(binaries, 3);
        //System.out.println("packageVersion: " + packageVersion);
        sumOfVersions += binToDecLong(packageVersion);

        String packetType = take(binaries, 3);
        System.out.println("packetType: " + binToDec(packetType));

        if (packetType.equals("100")) {
            String hexNumber = extractNumber(binaries);
            stackOfResults.push(binToDecLong(hexNumber));
        } else {
            //operator
            String indicator = binaries.poll();
            System.out.println("indicator: " + indicator);
            int amoutOfelementsInBlock = 0;
            if (indicator.equals("0")) {
                String binaryLengh = take(binaries, 15);
                //System.out.println("length: " + binaryLengh);
                Integer length = binToDec(binaryLengh);

                int sizeToStop = binaries.size() - length;
                while (binaries.size() > sizeToStop) {
                    readPacket(binaries);
                    amoutOfelementsInBlock++;
                }
            } else {
                String binaryAmount = take(binaries, 11);
                //System.out.println("binary amount: " + binaryAmount);
                Integer amount = binToDec(binaryAmount);
                for (int i = 0; i < amount; i++) {
                    readPacket(binaries);
                    amoutOfelementsInBlock++;
                }
            }
            if (packetType.equals("000")) {
                Long result = 0L;
                for (int i = 0; i < amoutOfelementsInBlock; i++) {
                    result += stackOfResults.pop();
                }
                stackOfResults.add(result);
            } else if (packetType.equals("001")) {
                Long result = 1L;
                for (int i = 0; i < amoutOfelementsInBlock; i++) {
                    result *= stackOfResults.pop();
                }
                stackOfResults.add(result);
            } else if (packetType.equals("010")) {
                Long result = stackOfResults.pop();
                for (int i = 0; i < amoutOfelementsInBlock - 1; i++) {
                    Long tmp = stackOfResults.pop();
                    if (tmp < result) {
                        result = tmp;
                    }
                }
                stackOfResults.add(result);
            } else if (packetType.equals("011")) {
                Long result = stackOfResults.pop();
                for (int i = 0; i < amoutOfelementsInBlock - 1; i++) {
                    Long tmp = stackOfResults.pop();
                    if (tmp > result) {
                        result = tmp;
                    }
                }
                stackOfResults.add(result);
            } else if (packetType.equals("101")) {
                Long second = stackOfResults.pop();
                Long first = stackOfResults.pop();
                stackOfResults.add(first > second ? 1L : 0L);
            } else if (packetType.equals("110")) {
                Long second = stackOfResults.pop();
                Long first = stackOfResults.pop();
                stackOfResults.add(first < second ? 1L : 0L);
            } else if (packetType.equals("111")) {
                Long second = stackOfResults.pop();
                Long first = stackOfResults.pop();
                stackOfResults.add(Objects.equals(first, second) ? 1L : 0L);
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
        //System.out.println("Extracting number");
        StringBuilder sb = new StringBuilder();
        String isLastIndicator = binaries.poll();
        //System.out.println("isLast: " + isLastIndicator);
        while (!isLastIndicator.equals("0")) {
            String number = take(binaries, 4);
            //System.out.println("number: " + number);
            sb.append(number);
            isLastIndicator = binaries.poll();
            //System.out.println("isLast: " + isLastIndicator);
        }
        String number = take(binaries, 4);
        sb.append(number);
        //System.out.println("number: " + number);
        //System.out.println(sb);
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
