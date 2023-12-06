package org.lab.models.parking;

import org.lab.models.transport.Car;
import org.lab.models.transport.Vehicle;
import org.lab.utils.ThreadLogger;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot implements Parkable {
    private final List<ParkingSpot> parkingSpots = new ArrayList<>();

    public ParkingLot(int freeParkingSpots) {
        for (int i = 0; i < freeParkingSpots; i++) {
            parkingSpots.add(new ParkingSpot());
        }
    }

    public List<ParkingSpot> getTakenParkingSpots() {
        return parkingSpots.stream().filter(parkingSpot -> !parkingSpot.isAvailable()).toList();
    }

    public void addParkingSpot(ParkingSpot parkingSpot) {
        parkingSpots.add(parkingSpot);
    }

    @Override
    public synchronized void parkCar(Car car) {
        for (ParkingSpot parkingSpot : parkingSpots) {
                if (parkingSpot.isAvailable()) {
                    parkingSpot.putCarOnSpot(car);
                    car.setIsParked(true);
                break;
            }
        }
    }

    @Override
    public synchronized void leaveParkingSpot(Car car) {
            for (ParkingSpot parkingSpot : parkingSpots) {
                if (parkingSpot.getCarOnSpot() == car) {
                    parkingSpot.removeCarFromSpot();
                    car.setIsParked(false);
                    break;
                }
            }
    }
}
