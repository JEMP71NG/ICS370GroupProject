package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import org.json.JSONObject;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ClubRentalController {
    @FXML
    private ListView<String> clubListView;
    @FXML
    private TextField rentalQuantityField;

    private Map<String, Integer> clubInventory = new HashMap<>();

    public void initialize() {
        loadClubInventory();
        populateClubListView();
    }

    private void loadClubInventory() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("src/main/resources/com/example/demo/clubInventory.json")));
            JSONObject jsonObject = new JSONObject(content);
            Iterator<String> keys = jsonObject.keys();

            while (keys.hasNext()) {
                String key = keys.next();
                int value = jsonObject.getInt(key);
                clubInventory.put(key, value);
            }
        } catch (IOException e) {
            showAlert("Error", "Could not load club inventory.");
            e.printStackTrace();
        }
    }

    private void populateClubListView() {
        clubListView.getItems().clear();
        clubInventory.forEach((club, quantity) -> clubListView.getItems().add(club + " - " + quantity + " available"));
    }

    @FXML
    private void rentClub() {
        String selectedClub = clubListView.getSelectionModel().getSelectedItem();
        if (selectedClub != null && !rentalQuantityField.getText().isEmpty()) {
            try {
                int quantityToRent = Integer.parseInt(rentalQuantityField.getText());
                String clubName = selectedClub.split(" - ")[0];
                int availableQuantity = clubInventory.getOrDefault(clubName, 0);

                if (quantityToRent <= availableQuantity) {
                    clubInventory.put(clubName, availableQuantity - quantityToRent);
                    updateClubInventoryFile();
                    populateClubListView();
                } else {
                    showAlert("Error", "Requested quantity exceeds available stock.");
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Please enter a valid quantity.");
            }
        }
    }

    private void updateClubInventoryFile() {
        try (FileWriter file = new FileWriter("src/main/resources/com/example/demo/clubInventory.json")) {
            JSONObject jsonObject = new JSONObject(clubInventory);
            file.write(jsonObject.toString(4));  // Pretty-print with indentation
        } catch (IOException e) {
            showAlert("Error", "Could not update club inventory.");
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}