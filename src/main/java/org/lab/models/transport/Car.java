package org.lab.models.transport;

import org.lab.models.parking.ParkingLot;
import org.lab.utils.ThreadLogger;

import java.util.Random;

public class Car extends ObservableVehicle {

    private final String carName;
    private final Random random = new Random();
    private ParkingLot parkingLot = null;
    private final int timeToDrive = random.nextInt(5, 13);
    private final int timeToStayParked = random.nextInt(6, 16);
    public Object lock;
    private boolean isPaused = false;
    private boolean isParked = false;


    public Car(String carName) {
        this.carName = carName;
    }

    public void selectParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public String getCarName() {
        return carName;
    }
    public void setIsParked(boolean isParked) {
        this.isParked = isParked;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(carName);
        Thread.currentThread().setPriority(random.nextInt(1, 11));

        if (parkingLot == null) {
            setCurrentState("No parking lot selected");
            return;
        }

        while (true) {
            if (isPaused) {
                synchronized (lock) {
                    try {
                        while (isPaused) {
                            setCurrentState("Car is paused");
//                            ThreadLogger.log("Car is paused");
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        setCurrentState("Car was interrupted while parking");
                    }
                }
                continue;
            }
            var isSuccededParked = drive();
            if (!isSuccededParked) {
                continue;
            }
            stayParked();
            drive();
        }
    }


    public void pause() {
        isPaused = true;
    }

    public void unpause() {
        isPaused = false;
        if (isParked) {
            parkingLot.leaveParkingSpot(this);
        }
        isParked = false;
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    private boolean drive() {
        setCurrentState("Car is driving");
        if (!isParked) {
            setCurrentState("Car is trying to park");
            parkingLot.parkCar(this);
        } else {
            setCurrentState("Car leaved parking spot");
            parkingLot.leaveParkingSpot(this);
        }
        return this.isParked;
    }

    public void stayParked() {
        setCurrentState("Car is parked for " + timeToStayParked + " seconds");
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;

        synchronized (lock) {
            while (elapsedTime < timeToStayParked * 1000L && isParked) {
                try {
                    lock.wait(timeToStayParked * 1000L - elapsedTime);
                } catch (InterruptedException e) {
                    setCurrentState("Car was interrupted while being parked");
                    return; // Exit the method on interruption
                }

                // Calculate elapsed time
                elapsedTime = System.currentTimeMillis() - startTime;
            }
        }

//        synchronized (lock) {
//            try {
//                lock.wait(timeToStayParked * 1000L);
//            } catch (InterruptedException e) {
////                Thread.currentThread().interrupt();
//                setCurrentState("Car was interrupted while being parked");
//            }
//        }
    }
}
