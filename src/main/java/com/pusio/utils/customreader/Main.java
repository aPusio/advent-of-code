package com.pusio.utils.customreader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader("src/main/resources/day5.input"));
        String line;
        // Read numbers from the line
        while ((line = fileReader.readLine()) != null) { // Stop reading file when -1 is reached
            Scanner scanner = new Scanner(line);
            scanner.useDelimiter(",| -> ");

            // First input is the start
            System.out.print(scanner.nextInt() + ",");
            System.out.print(scanner.nextInt() + "->");
//                System.out.print(scanner.next());
            System.out.print(scanner.nextInt() + ",");
            System.out.print(scanner.nextInt());
            System.out.println();

        }

    }
}
