
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GolfController implements Initializable {

    @FXML
    private Label welcomeText;

    @FXML
    private TextField memberNameField;

    @FXML
    private TextField teeTimeField;

    @FXML
    private ListView<String> reservationsListView;

    private TeeTime teeTime;

    @FXML
    protected void onReserveButtonClick() {
        String memberName = memberNameField.getText();
        int teeTimeValue = Integer.parseInt(teeTimeField.getText());

        if (teeTime == null) {
            teeTime = new TeeTime(teeTimeValue, 1, new ArrayList<>());
        }

        member newMember = new member(memberName, teeTime.getMembers().size() + 1);
        boolean reserved = teeTime.reserve(newMember);
        if (reserved) {
            updateReservationsList();
            welcomeText.setText("Reservation successful for " + memberName);
        } else {
            welcomeText.setText("Tee time is full. Reservation failed.");
        }
    }

    @FXML
    protected void onCancelButtonClick() {
        if (teeTime == null) {
            welcomeText.setText("No reservations to cancel.");
            return;
        }

        String memberName = memberNameField.getText();

        for (member m : teeTime.getMembers()) {
            if (m.getName().equals(memberName)) {
                teeTime.cancelReservation(m);
                updateReservationsList();
                welcomeText.setText("Reservation canceled for " + memberName);
                return;
            }
        }
        welcomeText.setText("Member not found in reservation.");
    }

    private void updateReservationsList() {
        reservationsListView.getItems().clear();
        if (teeTime != null) {
            for (member m : teeTime.getMembers()) {
                reservationsListView.getItems().add(m.getName() + " - Tee Time: " + teeTime.getTime());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize components if needed
        updateReservationsList();
    }
}
