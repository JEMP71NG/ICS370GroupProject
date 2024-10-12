
package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import com.example.demo.domain.TeeTime;
import com.example.demo.domain.member;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class GolfController implements Initializable {

    @FXML
    private Label welcomeText;

    @FXML
    private TextField memberNameField;

    @FXML
    private TextField teeTimeField;

    @FXML
    private ListView<String> reservationsListView;

    // Map to store tee times associated with different tee time slots
    private Map<Integer, TeeTime> teeTimes = new HashMap<>();

    @FXML
    protected void onReserveButtonClick() {
        String memberName = memberNameField.getText();
        int teeTimeValue = Integer.parseInt(teeTimeField.getText());

        // Get the tee time object for the specified time or create a new one if it doesn't exist
        TeeTime teeTime = teeTimes.getOrDefault(teeTimeValue, new TeeTime(teeTimeValue, 1, new ArrayList<>()));

        member newMember = new member(memberName, teeTime.getMembers().size() + 1);
        boolean reserved = teeTime.reserve(newMember);

        if (reserved) {
            teeTimes.put(teeTimeValue, teeTime); // Store the updated tee time
            updateReservationsList();
            welcomeText.setText("Reservation successful for " + memberName + " at tee time " + teeTimeValue);
        } else {
            welcomeText.setText("Tee time is full. Reservation failed.");
        }
    }

    @FXML
    protected void onCancelButtonClick() {
        String memberName = memberNameField.getText();
        int teeTimeValue = Integer.parseInt(teeTimeField.getText());

        TeeTime teeTime = teeTimes.get(teeTimeValue);

        if (teeTime != null) {
            for (member m : teeTime.getMembers()) {
                if (m.getName().equals(memberName)) {
                    teeTime.cancelReservation(m);
                    if (teeTime.getMembers().isEmpty()) {
                        teeTimes.remove(teeTimeValue); // Remove empty tee time
                    } else {
                        teeTimes.put(teeTimeValue, teeTime); // Update tee time
                    }
                    updateReservationsList();
                    welcomeText.setText("Reservation canceled for " + memberName);
                    return;
                }
            }
            welcomeText.setText("Member not found in reservation.");
        } else {
            welcomeText.setText("No reservations found for the given tee time.");
        }
    }

    private void updateReservationsList() {
        reservationsListView.getItems().clear();

        // Sort the tee times by time (keys in the map)
        teeTimes.keySet().stream()
                .sorted() // This will sort the keys (tee time values) in ascending order
                .forEach(teeTimeValue -> {
                    TeeTime teeTime = teeTimes.get(teeTimeValue);
                    for (member m : teeTime.getMembers()) {
                        reservationsListView.getItems().add(m.getName() + " - Tee Time: " + teeTime.getTime());
                    }
                });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize components if needed
        updateReservationsList();
    }
}
