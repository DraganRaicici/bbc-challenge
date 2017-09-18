package com.bbc;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Start introducing URLs from the keyboard.");
        System.out.println("URLS are separated by new lines.");
        System.out.println("Caution is advised as empty lines will be treated as URLs as well.");
        System.out.println("The word 'Done' entered in a new line, at the end, will start the requests.");

        List<String> addresses = CommandLineReader.read();

        ThreadStarter ts = ThreadStarter.getInstance();
        ts.start(addresses);
    }

}
