package org.lab.models.transport;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public abstract class ObservableVehicle extends Thread implements Vehicle {
    private boolean isParked = false;
    private final StringProperty currentState = new SimpleStringProperty("Car is not running");

    public String getCurrentState() {
        return currentState.get();
    }

    public void setCurrentState(String currentState) {
        this.currentState.set(currentState);
    }

    @Override
    public void addListener(InvalidationListener listener) {
        currentState.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        currentState.removeListener(listener);
    }

    @Override
    public abstract void run();

    public boolean isParked() {
        return isParked;
    }

    public void setParked(boolean parked) {
        isParked = parked;
    }
}