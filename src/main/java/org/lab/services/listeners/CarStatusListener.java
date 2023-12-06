package org.lab.services.listeners;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableView;
import org.lab.models.threading.ThreadStatus;
import org.lab.utils.ThreadLogger;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CarStatusListener implements InvalidationListener {

    private final String carName;
    private final TableView<ThreadStatus> table;
    private final Lock lock = new ReentrantLock();

    public CarStatusListener(String carName, TableView<ThreadStatus> table) {
        this.table = table;
        this.carName = carName;
    }

    @Override
    public void invalidated(Observable observable) {
        ThreadLogger.log(((StringProperty)observable).getValue());
        if (lock.tryLock()) {
            try {
                table.getItems().stream().filter(threadStatus -> threadStatus.getName().equals(carName)).forEach(threadStatus -> {
                    threadStatus.setLastMessage(((StringProperty) observable).getValue());
                    threadStatus.setPriority(Thread.currentThread().getPriority());
                    table.refresh();
                });
            } finally {
                lock.unlock();
            }
        }
    }
}
