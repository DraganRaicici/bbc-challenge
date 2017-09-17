package com.bbc;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Write new line separated input.");
        System.out.println("The word 'Done' entered in a new line at the end will start the requests.");

        List<String> addresses = CommandLineReader.read();

        ThreadStarter ts = ThreadStarter.getInstance();
        ts.start(addresses);
    }

}
