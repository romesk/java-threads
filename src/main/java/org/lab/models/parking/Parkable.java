package org.lab.models.parking;


import org.lab.models.transport.Car;

public interface Parkable {
    void parkCar(Car car);
    void leaveParkingSpot(Car car);
}
