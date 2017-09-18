package com.bbc;

import java.util.List;

/**
 * The ThreadStarter is the class responsible with starting threads sequentially and preventing the application from
 * creating 50 threads at one time, sticking to one at a time until it finishes its request or times out.
 */
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
        //For each URL in the list, start a new request thread
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
