package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import models.Challenge;
import models.User;
import utils.ChallengeDetailsFetcher;
import utils.LeaderboardFetcher;

public class AdminController {

    @FXML
    private ComboBox<User> userComboBox;
    @FXML
    private ComboBox<Challenge> challengeComboBox;
    @FXML
    private Button verifyButton;

    private LeaderboardFetcher leaderboardFetcher = new LeaderboardFetcher();
    private ChallengeDetailsFetcher challengeDetailsFetcher = new ChallengeDetailsFetcher();

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        ObservableList<User> users = FXCollections.observableArrayList(leaderboardFetcher.getAllUsers());
        ObservableList<Challenge> challenges = FXCollections.observableArrayList(challengeDetailsFetcher.getAllChallenges());

        userComboBox.setItems(users);
        challengeComboBox.setItems(challenges);
    }

    @FXML
    private void verifyAndAddPoints() {
        User selectedUser = userComboBox.getValue();
        Challenge selectedChallenge = challengeComboBox.getValue();

        if (selectedUser == null || selectedChallenge == null) {
            showError("Invalid Selection", "Please select both a user and a challenge.");
            return;
        }

        // Periksa apakah tantangan sudah diverifikasi
        if (leaderboardFetcher.isChallengeVerified(selectedUser.getId(), selectedChallenge.getId())) {
            showError("Error", "Challenge already verified for this user.");
            return;
        }

        boolean success = leaderboardFetcher.verifyAndAddPoints(selectedUser.getName(), selectedChallenge.getPoints());
        if (success) {
            leaderboardFetcher.markChallengeAsVerified(selectedUser.getId(), selectedChallenge.getId());
            showAlert("Success", "Points added successfully for user: " + selectedUser.getName());
            if (mainController != null) {
                mainController.updateLeaderboard();
            }
        } else {
            showError("Error", "Failed to update points for user: " + selectedUser.getName());
        }
    }

    private void refreshLeaderboard() {
        // Logic to refresh leaderboard
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
