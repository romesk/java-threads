package org.lab.models.transport;

import javafx.beans.Observable;

public interface Vehicle extends Runnable, Observable {
    void run();
}
