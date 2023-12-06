package org.lab.services;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.lab.models.threading.ThreadStatus;

public class GuiManager {
    @FXML private static TableView<ThreadStatus> table;

    public static void setTable(TableView<ThreadStatus> table) {
        GuiManager.table = table;
    }

    public static TableView<ThreadStatus> getTable() {
        return table;
    }

    @FXML
    static void addThreadRow(ThreadStatus threadStatus) {
        if (table == null) {
            System.out.println("Table is null");
            return;
        }

        table.getItems().add(threadStatus);
    }

    @FXML
    static void updateThreadState(String threadName, Thread.State state) {
        if (table == null) {
            System.out.println("Table is null");
            return;
        }

        table.getItems().stream().filter(threadStatus -> threadStatus.getName().equals(threadName)).forEach(threadStatus -> {
            threadStatus.setState(state);
            table.refresh();
        });
    }

}
