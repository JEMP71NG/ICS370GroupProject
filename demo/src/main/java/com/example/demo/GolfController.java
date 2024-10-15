
package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import com.example.demo.domain.TeeTime;
import com.example.demo.domain.member;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GolfController {

    @FXML
    private TextField memberNameField;

    @FXML
    private TextField dateTimeField;

    @FXML
    private ListView<String> reservationsListView;

    @FXML
    private Label welcomeText;

    // Map to store tee times associated with different date and time slots
    private Map<LocalDateTime, TeeTime> teeTimes = new HashMap<>();

    @FXML
    protected void onReserveButtonClick() {
        String memberName = memberNameField.getText();
        String dateTimeString = dateTimeField.getText(); // User input for date and time

        // Validate that memberName is not empty
        if (memberName == null || memberName.trim().isEmpty()) {
            welcomeText.setText("Please enter a member name.");
            return;
        }

        // Validate that the dateTimeString is in the correct format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a");
        LocalDateTime teeTimeValue;
        try {
            teeTimeValue = LocalDateTime.parse(dateTimeString, formatter);
        } catch (Exception e) {
            welcomeText.setText("Invalid date and time. Please use MM/DD/YYYY h:mm AM/PM format.");
            return;
        }

        TeeTime teeTime = teeTimes.getOrDefault(teeTimeValue, new TeeTime(teeTimeValue, 1, new ArrayList<>()));
        member newMember = new member(memberName, teeTime.getMembers().size() + 1);

        boolean reserved = teeTime.reserve(newMember);

        if (reserved) {
            teeTimes.put(teeTimeValue, teeTime);
            updateReservationsList();
            welcomeText.setText("Reservation successful for " + memberName + " at " + dateTimeString);
        } else {
            welcomeText.setText("Tee time is full. Reservation failed.");
        }
    }

    @FXML
    /*protected void onCancelButtonClick() {
        String memberName = memberNameField.getText();
        String dateTimeString = dateTimeField.getText();

        // Validate that the dateTimeString is in the correct format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a");
        LocalDateTime teeTimeValue;
        try {
            teeTimeValue = LocalDateTime.parse(dateTimeString, formatter);
        } catch (Exception e) {
            welcomeText.setText("Invalid date and time. Please use MM/DD/YYYY h:mm AM/PM format.");
            return;
        }

        TeeTime teeTime = teeTimes.get(teeTimeValue);

        if (teeTime != null) {
            for (member m : teeTime.getMembers()) {
                if (m.getName().equals(memberName)) {
                    teeTime.cancelReservation(m);
                    if (teeTime.getMembers().isEmpty()) {
                        teeTimes.remove(teeTimeValue);
                    } else {
                        teeTimes.put(teeTimeValue, teeTime);
                    }
                    updateReservationsList();
                    welcomeText.setText("Reservation canceled for " + memberName);
                    return;
                }
            }
            welcomeText.setText("Member not found in reservation.");
        } else {
            welcomeText.setText("No reservations found for the given date and time.");
        }
    }*/

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
