package org.lab.utils;

import org.lab.models.transport.Car;

import java.util.List;

public class ThreadUtils {
    public static Thread findThreadByName(String threadName) {
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        int numThreads = currentGroup.activeCount();
        Thread[] threads = new Thread[numThreads];
        currentGroup.enumerate(threads);
        for (Thread thread : threads) {
            if (thread != null && thread.getName().equals(threadName)) {
                return thread;
            }
        }
        return null;
    }

    public static Car findCarByName(List<Car> cars, String carName) {
        for (Car car : cars) {
            if (car.getCarName().equals(carName)) {
                return car;
            }
        }
        return null;
    }

    public static boolean isAnyThreadAlive(List<Car> threads) {
        for (Thread thread : threads) {
            if (thread.isAlive()) {
                return true;
            }
        }
        System.out.println("All threads are dead");
        return false;
    }
}
