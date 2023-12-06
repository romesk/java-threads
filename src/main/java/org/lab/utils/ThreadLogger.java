package org.lab.utils;

import java.time.LocalTime;

public class ThreadLogger {
    public static void log(String message) {
        var currentThread = Thread.currentThread();
        String currentTime = LocalTime.now().toString();
        System.out.println("[" + currentTime + "] " + currentThread.getName() + "(" + currentThread.getPriority() + ")" + ": " + message);
    }
}


