package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.DatePicker;
import com.example.demo.domain.TeeTime;
import com.example.demo.domain.member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GolfController {

    @FXML
    private TextField memberNameField;

    @FXML
    private DatePicker dateTimeField;

    @FXML
    private TextField timeField;

    @FXML
    private ListView<String> reservationsListView;

    @FXML
    private Label welcomeText;

    // Map to store tee times associated with different date and time slots
    private final Map<LocalDateTime, TeeTime> teeTimes = new HashMap<>();

    @FXML
    protected void onReserveButtonClick() {
        String memberName = memberNameField.getText();

        // Get the selected date from the DatePicker
        LocalDate selectedDate = dateTimeField.getValue();
        if (selectedDate == null) {
            welcomeText.setText("Please choose a date.");
            return;
        }

        // Get the time from the TextField
        String timeString = timeField.getText();
        LocalTime selectedTime;
        try {
            selectedTime = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm")); // Parses time in HH:mm format
        } catch (Exception e) {
            welcomeText.setText("Invalid time format. Please use HH:mm with the 24 Hour format.");
            return;
        }

        // Create LocalDateTime using selected date and time
        LocalDateTime teeTimeValue = LocalDateTime.of(selectedDate, selectedTime);

        // Validate that memberName is not empty
        if (memberName == null || memberName.trim().isEmpty()) {
            welcomeText.setText("Please enter a member name.");
            return;
        }

        TeeTime teeTime = teeTimes.getOrDefault(teeTimeValue, new TeeTime(teeTimeValue, 1, new ArrayList<>()));
        member newMember = new member(memberName, teeTime.getMembers().size() + 1);

        boolean reserved = teeTime.reserve(newMember);

        if (reserved) {
            teeTimes.put(teeTimeValue, teeTime);
            updateReservationsList();
            welcomeText.setText("Reservation successful for " + memberName + " at " + teeTimeValue);
        } else {
            welcomeText.setText("Tee time is full. Reservation failed.");
        }
    }

    @FXML
    protected void onCancelButtonClick() {
        String selectedReservation = reservationsListView.getSelectionModel().getSelectedItem();

        if (selectedReservation != null) {
            // Extract member name and tee time from the selected reservation string
            String[] parts = selectedReservation.split(" - Tee Time: ");
            String memberName = parts[0];
            String teeTimeKey = parts[1];  // Assuming the format is "MemberName - Tee Time: teeTimeKey"

            TeeTime teeTime = teeTimes.get(LocalDateTime.parse(teeTimeKey, DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a")));

            if (teeTime != null) {
                // Remove the member from the tee time
                teeTime.getMembers().removeIf(m -> m.getName().equals(memberName));

                // Update or remove the tee time in the map
                if (teeTime.getMembers().isEmpty()) {
                    teeTimes.remove(LocalDateTime.parse(teeTimeKey, DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a"))); // Remove empty tee time
                } else {
                    teeTimes.put(LocalDateTime.parse(teeTimeKey, DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a")), teeTime); // Update tee time
                }

                updateReservationsList();
                welcomeText.setText("Reservation canceled for " + memberName);
            } else {
                welcomeText.setText("No reservations found for the given tee time.");
            }
        } else {
            welcomeText.setText("Please select a reservation to cancel.");
        }
    }

    private void updateReservationsList() {
        reservationsListView.getItems().clear();
        teeTimes.keySet().stream()
                .sorted() // Sort by date and time
                .forEach(teeTimeValue -> {
                    TeeTime teeTime = teeTimes.get(teeTimeValue);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a");
                    for (member m : teeTime.getMembers()) {
                        reservationsListView.getItems().add(m.getName() + " - Tee Time: " + teeTime.getTeeTime().format(formatter));
                    }
                });
    }
}
