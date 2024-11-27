package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.Challenge;
import models.User;
import models.News;
import dao.NewsDAO;
import utils.ChallengeDetailsFetcher;
import utils.LeaderboardFetcher;

public class AdminController {

    @FXML
    private ComboBox<User> userComboBox;
    @FXML
    private ComboBox<Challenge> challengeComboBox;
    @FXML
    private Button verifyButton;
    @FXML
    private TextField newsTitleField;
    @FXML
    private TextField newsDescriptionField;

    private LeaderboardFetcher leaderboardFetcher = new LeaderboardFetcher();
    private ChallengeDetailsFetcher challengeDetailsFetcher = new ChallengeDetailsFetcher();
    private NewsDAO newsDAO = new NewsDAO();

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

    @FXML
    private void addNews() {
        String title = newsTitleField.getText();
        String description = newsDescriptionField.getText();
        if (title.isEmpty() || description.isEmpty()) {
            showError("Error", "Title and description cannot be empty.");
            return;
        }
        News news = new News(0, 1, title, description, null); // Assuming adminId is 1
        newsDAO.addNews(news);
        showInfo("Success", "News added successfully.");
        // Refresh UI if necessary
    }

    @FXML
    private void updateNews() {
        // Implement update logic
    }

    @FXML
    private void deleteNews() {
        // Implement delete logic
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

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
