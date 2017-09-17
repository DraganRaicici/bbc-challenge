package com.bbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLineReader {

    public static List<String> read() {

        Scanner input = new Scanner(System.in);
        List<String> lines = new ArrayList<>();
        String lineNew;

        while (input.hasNextLine()) {
            lineNew = input.nextLine();
            if (lineNew.equals("Done")) {
                break;
            }
            lines.add(lineNew);
        }
        return lines;
    }
}
