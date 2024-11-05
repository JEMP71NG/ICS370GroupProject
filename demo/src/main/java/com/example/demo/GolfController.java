package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GolfController {

    @FXML
    private TextField nameField;
    @FXML
    private DatePicker dateTimeField;
    @FXML
    private TextField timeField;
    @FXML
    private Button confirmReservationButton;
    @FXML
    private Button cancelReservationButton;

    @FXML
    private TableView<TeeTime> teeTimeTable;
    @FXML
    private TableColumn<TeeTime, String> nameColumn;
    @FXML
    private TableColumn<TeeTime, String> dateColumn;
    @FXML
    private TableColumn<TeeTime, String> timeColumn;

    private ObservableList<TeeTime> teeTimes;

    @FXML
    public void initialize() {
        teeTimes = FXCollections.observableArrayList();
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        teeTimeTable.setItems(teeTimes);
    }

    @FXML
    private void confirmReservation() {
        String name = nameField.getText();
        LocalDate date = dateTimeField.getValue();
        String time = timeField.getText();

        if (name.isEmpty() || date == null || time.isEmpty()) {
            showAlert("Incomplete Information", "Please enter your name, select a date, and enter a time.");
            return;
        }

        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        TeeTime teeTime = new TeeTime(name, formattedDate, time);
        teeTimes.add(teeTime);

        nameField.clear();
        dateTimeField.setValue(null);
        timeField.clear();

        showAlert("Reservation Confirmed", "Tee time reserved for " + name + " on " + formattedDate + " at " + time);
    }

    @FXML
    private void cancelReservation() {
        TeeTime selectedTeeTime = teeTimeTable.getSelectionModel().getSelectedItem();
        if (selectedTeeTime == null) {
            showAlert("No Selection", "Please select a tee time to cancel.");
            return;
        }

        teeTimes.remove(selectedTeeTime);
        showAlert("Reservation Cancelled", "The selected tee time reservation has been cancelled.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class TeeTime {
        private final String name;
        private final String date;
        private final String time;

        public TeeTime(String name, String date, String time) {
            this.name = name;
            this.date = date;
            this.time = time;
        }

        public String getName() {
            return name;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }
    }
}
