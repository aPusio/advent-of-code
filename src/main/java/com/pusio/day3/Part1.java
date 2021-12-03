package com.pusio.day3;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://adventofcode.com/2021/day/3
 */
public class Part1 {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/day3.input");
        List<String> contents = FileUtils.readLines(file, "UTF-8");
        Map<Integer, String> bits = calculateBitsMap(contents);

        StringBuilder gammaRateBuilder = new StringBuilder();
        StringBuilder epsilonRateBuilder = new StringBuilder();

        for (String value : bits.values()) {
            if(StringUtils.countMatches(value, "0") > StringUtils.countMatches(value, "1")){
                gammaRateBuilder.append("0");
                epsilonRateBuilder.append("1");
            }else{
                gammaRateBuilder.append("1");
                epsilonRateBuilder.append("0");
            }
        }

        int gamma = Integer.parseInt(gammaRateBuilder.toString(), 2);
        int epsilon = Integer.parseInt(epsilonRateBuilder.toString(), 2);
        System.out.println("result: " + gamma * epsilon);
    }

    private static Map<Integer, String> calculateBitsMap(List<String> copy) {
        Map<Integer, String> bits = new HashMap<>();
        for (String content : copy) {
            for (int i = 0; i < content.length(); i++) {
                String storedBit = bits.getOrDefault(i, "");
                storedBit += content.charAt(i);
                bits.put(i, storedBit);
            }
        }
        return bits;
    }
}
