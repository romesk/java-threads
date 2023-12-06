package org.lab.models.parking;

import org.lab.models.transport.Vehicle;

public class ParkingSpot {
    private boolean isAvailable;
    private Vehicle car;

    public ParkingSpot() {
        this.isAvailable = true;
    }

    public void putCarOnSpot(Vehicle car) {
        this.car = car;
        this.isAvailable = false;
    }

    public Vehicle getCarOnSpot() {
        return car;
    }

    public void removeCarFromSpot() {
        this.car = null;
        this.isAvailable = true;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public Vehicle getCar() {
        return car;
    }
}
