package com.pusio.day17.part1;

import com.google.common.base.Splitter;
import com.pusio.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day17Part1Solution {
    private List<Integer> parsedNumbers = new ArrayList<>();
    private Long sumOfVersions = 0L;

    public Long solve(String filePath) {
        String hexLine = Utils.readLines(filePath).stream().findFirst().orElseThrow();
        List<String> binaryList = Splitter.fixedLength(1).splitToList(hexLine)
                .stream().map(this::hexToBin)
                .flatMap(a -> Splitter.fixedLength(1).splitToStream(a))
                .collect(Collectors.toList());

        readPacket(binaryList);
//        System.out.println(integer);
//        System.out.println(parsedNumbers);
        return sumOfVersions;
    }

    private Integer readPacket(List<String> binaryList) {
        System.out.println("INPUT: " + binaryList);
        String version = String.join("", binaryList.subList(0, 3));
        System.out.println("VERSION: " + binToDecLong(version));
        sumOfVersions += binToDecLong(version);
        String Id = String.join("", binaryList.subList(3, 6));
        List<String> packet = binaryList.subList(6, binaryList.size());
        if (binToDec(Id) == 4) {
            return extractNumber(packet);
        } else {
            //operator
            String indicator = packet.get(0);
            if (indicator.equals("0")) {
                System.out.println("Contains sub numbers");
                //15 bit number
                int parsedSize = binToDec(String.join("", packet.subList(1, 16)));
                System.out.println("Parsed size: " + parsedSize);
                int maxPosition = parsedSize + 16;
                System.out.println("max position: " + maxPosition);
                Integer actualPosition = 16;
//                List<String> subPackage = packet.subList(actualPosition, size);
                List<String> subPackage;
                do {
                    subPackage = packet.subList(actualPosition, maxPosition);
                    Integer readed = readPacket(subPackage);
                    actualPosition += readed;
//                    containsDigitOne = subPackage.stream().anyMatch(a -> a.equals("1"));
                } while (actualPosition < maxPosition);

                System.out.println("Actual Position: " + actualPosition);
                return actualPosition;
            }
            System.out.println("Contains sub sequences");
            //11 bit number of subpackages
            int parsedSize = binToDec(String.join("", packet.subList(1, 12)));
//            int maxPosition = parsedSize + 12;
            Integer actualPosition = 12;
            List<String> subPackage;
            for (int i = 0; i < parsedSize; i++) {
                subPackage = packet.subList(actualPosition, packet.size() - 1);
                Integer readed = readPacket(subPackage);
                if (readed != null) {
                    actualPosition += readed;
                }
            }
        }
        return null;
    }

    private Integer extractNumber(List<String> packet) {
        System.out.println("Extracting number");
        List<String> resutl = new ArrayList<>();
        boolean isLast = false;
        int i = 0;
        //change it on while
        for (; isLast != true; i += 5) {
            isLast = packet.get(i).equals("0") ? true : false;
            resutl.addAll(packet.subList(i + 1, i + 5));
        }
        if (resutl.isEmpty()) {
            return null;
        }
        parsedNumbers.add(binToDec(String.join("", resutl)));
        //6 is Version + ID length
        return i + 6;
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
