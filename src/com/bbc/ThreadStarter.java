package com.bbc;

import java.util.List;

public class ThreadStarter {
    private static ThreadStarter instance = null;
    private ThreadStarter() {
    }
    public static ThreadStarter getInstance( ) {
        if(instance == null) {
            instance = new ThreadStarter();
        }
        return instance;
    }

    protected static void start(List<String> addresses) {
        for (String request: addresses) {
            Task task = new Task(request);
            Thread t = new Thread(task);
            t.start();
            try {
                //Have the threads start one after the other
                t.join();
            } catch (InterruptedException e) {
                System.err.println("Chain of calls interrupted.");
            }
        }
    }
}
