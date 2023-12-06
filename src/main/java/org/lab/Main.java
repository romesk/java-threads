package org.lab;

import org.lab.services.ParkingSimulator;

public class Main {
    public static void main(String[] args) {
        final var NUMBER_OF_PARKING_SPOTS = 3;
        var parkingSimulator = new ParkingSimulator(NUMBER_OF_PARKING_SPOTS);
        parkingSimulator.simulate();
    }
}