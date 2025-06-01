package com.hari.hack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> phrases = new ArrayList<>();

        System.out.println("Enter phrases (type 'exit' to finish):");

        while (true) {
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("exit")) {
                break;
            }
            if (!line.trim().isEmpty()) {
                phrases.add(line.trim());
            }
        }

        System.out.println("\nOutput:");
        System.out.println(phrases);
    }
}
