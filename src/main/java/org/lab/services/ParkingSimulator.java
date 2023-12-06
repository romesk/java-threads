package org.lab.services;

import org.lab.services.listeners.CarStatusListener;
import org.lab.models.parking.ParkingLot;
import org.lab.models.threading.ThreadStatus;
import org.lab.models.transport.Car;
import org.lab.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.List;

public class ParkingSimulator {
    private static final String CAR_THREAD_NAME = "Car ";
    private final ParkingLot parkingLot;
    private boolean isRunning = false;
    private final List<Car> cars;
    private final Object lock = new Object();

    /**
     * This constructor is used to create a parking lot with a given number of parking spots.
     * It also fills the parking lot with half of the given number of vehicles.
     * @param numberOfCars - the number of parking spots in the parking lot.
     */
    public ParkingSimulator(int numberOfCars) {
        var parkingSpotsNum = (int)Math.ceil((double) numberOfCars / 2);
        this.parkingLot = new ParkingLot(parkingSpotsNum);
        this.cars = generateCarsToPark(numberOfCars);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public void simulate() {
        System.out.println("Taken parking spots: " + parkingLot.getTakenParkingSpots().size());
        simulateParking();
        System.out.println("Taken parking spots: " + parkingLot.getTakenParkingSpots().size());
    }

    public void simulateParking() {
        System.out.println(Thread.currentThread().getName() + " started simulating parking");
        try {
            for (Car car : cars) {
                car.addListener(new CarStatusListener(car.getCarName(), GuiManager.getTable()));
                car.lock = lock;
                car.start();
                GuiManager.addThreadRow(new ThreadStatus(car.getCarName(), car.getState(), car.getPriority(), car.getCurrentState()));
            }
            Thread checkThreadStates = new Thread(this::checkThreadStates);
            checkThreadStates.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Car> generateCarsToPark(int numberOfVehicles) {
        List<Car> vehicles = new ArrayList<>();

        for (int i = 0; i < numberOfVehicles; i++) {
            Car car = new Car(CAR_THREAD_NAME + i);
            car.selectParkingLot(parkingLot);
            vehicles.add(car);
        }

        return vehicles;
    }

    public void pauseThread(String threadName) {
        Car thread = ThreadUtils.findCarByName(cars, threadName);
        if (thread != null) {
            System.out.println("Current thread name: " + Thread.currentThread().getName());
            System.out.println("Pausing thread " + thread.getName());
            thread.pause();
        }
    }

    public void resumeThread(String threadName) {
        Car thread = ThreadUtils.findCarByName(cars, threadName);
        if (thread != null) {
            System.out.println("Current thread name: " + Thread.currentThread().getName());
            System.out.println("Resuming thread " + thread.getName());
            thread.unpause();
        }
    }

    public void checkThreadStates() {
        while (ThreadUtils.isAnyThreadAlive(cars)) {
            for (Thread carThread : cars) {
                GuiManager.updateThreadState(carThread.getName(), carThread.getState());
            }
        }
        for (Thread carThread : cars) {
            GuiManager.updateThreadState(carThread.getName(), carThread.getState());
        }
    }
}
