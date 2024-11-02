package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import com.example.demo.domain.TeeTime;
import com.example.demo.domain.member;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HomeController {

    @FXML
    public void goToReserveTeeTime(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("first-view.fxml"));
            Parent firstViewRoot = loader.load();
            Scene firstViewScene = new Scene(firstViewRoot);

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(firstViewScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
