package com.example.demo;

import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import com.example.demo.domain.TeeTime;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void loginButtonClicked() {

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (LoginService.authenticate(username, password) != null) {
            LoginService.User user = LoginService.authenticate(username, password);

            if (user.role == LoginService.Role.MANAGER) {
                try {
                    JSONParser parser = new JSONParser();
                    JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("teeSheet.json"));

                    List<TeeTime> teeTimes = new ArrayList<>();
                    for (Object obj : jsonArray) {
                        JSONObject jsonObject = (JSONObject) obj;
                        String memberName = (String) jsonObject.get("memberName");
                        String date = (String) jsonObject.get("date");
                        String time = (String) jsonObject.get("time");
                        // ... extract other fields as needed ...


                    }
                } catch (IOException | ParseException e) {
                    showAlert("Error", "Failed to retrieve tee times.");
                }
            }  else if (user.role == LoginService.Role.MEMBER) {
                // Show only relevant tee times and enable reservation/cancellation
            }
            else {
                showAlert("Error", "Invalid credentials.");
            }
        }

    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
