package com.pusio.utils;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Utils {
    @SneakyThrows
    public static List<String> readUsingDelimiters(String filePath, String... delimiters) {
        BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
        List<String> result = new ArrayList<>();
        String line;
        while ((line = fileReader.readLine()) != null) {
            Scanner scanner = new Scanner(line);
            String delimiterRegex = String.join("|", Arrays.asList(delimiters));
            scanner.useDelimiter(delimiterRegex);
            while (scanner.hasNext()) {
                result.add(scanner.next());
            }
        }
        return result;
    }

    @SneakyThrows
    public static List<String> readLines(String filePath) {
        File file = new File(filePath);
        return FileUtils.readLines(file, "UTF-8");
    }

}
