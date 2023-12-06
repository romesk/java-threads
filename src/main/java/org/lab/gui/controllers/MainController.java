package org.lab.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.lab.models.threading.ThreadStatus;
import org.lab.services.GuiManager;
import org.lab.services.ParkingSimulator;


public class MainController {

    @FXML private javafx.scene.control.TextField threadsInput;
    @FXML private javafx.scene.control.TableView<ThreadStatus> table;
    private ParkingSimulator parkingSimulator;

    public void initialize() {
          table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
          table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("state"));
          table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("priority"));
          table.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("lastMessage"));
          table.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("lastStatusChangeTime"));

          for (TableColumn<ThreadStatus, ?> column : table.getColumns()) {
              column.setStyle("-fx-alignment: CENTER;");
          }

        GuiManager.setTable(table);
    }

    @FXML
    protected void onFieldKeyTyped() {
        if (!threadsInput.getText().isEmpty()) {
            try {
                int threads = Integer.parseInt(threadsInput.getText());
                if (threads < 1) {
                    threadsInput.setText("1");
                } else if (threads > 10) {
                    threadsInput.setText("10");
                }
            } catch (NumberFormatException e) {
                threadsInput.setText("1");
            }
        }
    }

    @FXML
    protected void onStartButtonClicked() {
        if (threadsInput.getText().isEmpty()) {
            threadsInput.setText("1");
        }

        if (parkingSimulator == null) {
            parkingSimulator = new ParkingSimulator(Integer.parseInt(threadsInput.getText()));
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Parking simulator is already running");
            alert.setContentText("Please wait until the current simulation is finished");
            alert.showAndWait();
            return;
        }

        // run the simulation in the background
        parkingSimulator.setIsRunning(true);
        parkingSimulator.simulate();
    }

    @FXML
    protected void onPauseButtonClicked() {
        if (parkingSimulator == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Parking simulator is not running");
            alert.setContentText("Please start the simulation first");
            alert.showAndWait();
            return;
        }

        // get selected row
        ThreadStatus selectedThread = table.getSelectionModel().getSelectedItem();
        if (selectedThread == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No thread selected");
            alert.setContentText("Please select a thread to pause");
            alert.showAndWait();
            return;
        }

        // pause the selected thread
        parkingSimulator.pauseThread(selectedThread.getName());
    }

    @FXML
    protected void onResumeButtonClicked() {
        if (parkingSimulator == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Parking simulator is not running");
            alert.setContentText("Please start the simulation first");
            alert.showAndWait();
            return;
        }

        // get selected row
        ThreadStatus selectedThread = table.getSelectionModel().getSelectedItem();
        if (selectedThread == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No thread selected");
            alert.setContentText("Please select a thread to resume");
            alert.showAndWait();
            return;
        }

        // resume the selected thread
        parkingSimulator.resumeThread(selectedThread.getName());
    }

}
