// File: /src/controllers/MainController.java
package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dao.NewsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Challenge;
import models.News;
import models.NewsFeed;
import models.User;
import utils.ChallengeDetailsFetcher;
import utils.DatabaseHelper;
import utils.LeaderboardFetcher;

public class MainController {

    @FXML
    private VBox leaderboardVBox;
    @FXML
    private AnchorPane contentPane;

    @FXML
    private Button firstPlaceButton;
    @FXML
    private Button secondPlaceButton;
    @FXML
    private Button thirdPlaceButton;

    @FXML
    private Label newsLabel1;
    @FXML
    private Label newsLabel2;
    @FXML
    private Label newsLabel3;

    @FXML
    private StackPane newsBox1;
    @FXML
    private StackPane newsBox2;
    @FXML
    private StackPane newsBox3;

    @FXML
    private Rectangle newsRect1;
    @FXML
    private Rectangle newsRect2;
    @FXML
    private Rectangle newsRect3;

    @FXML
    private Button newsDetailButton;

    private ChallengeDetailsFetcher detailsFetcher = new ChallengeDetailsFetcher();
    private LeaderboardFetcher leaderboardFetcher = new LeaderboardFetcher();
    private NewsFeed newsFeed;


    @FXML
    public void initialize() {
        newsFeed = new NewsFeed();
        loadNewsFromDatabase();
        newsBox1.setOnMouseClicked(event -> showNewsDetails("Waktu Hampir Habis untuk Selamatkan Hiu"));
        newsBox2.setOnMouseClicked(event -> showNewsDetails("Pemutihan Terumbu Karang Akibat Panas Laut"));
        newsBox3.setOnMouseClicked(event -> showNewsDetails("Penembak Singa Laut Diburu, yang Menemukan Dihadiahi Rp312 Juta"));
    }

    private void displayNews() {
        News news1 = newsFeed.getNewsById(1);
        News news2 = newsFeed.getNewsById(2);
        News news3 = newsFeed.getNewsById(3);

        if (news1 != null) newsLabel1.setText(news1.getTitle());
        if (news2 != null) newsLabel2.setText(news2.getTitle());
        if (news3 != null) newsLabel3.setText(news3.getTitle());
    }

    private void showNewsDetails(String title) {
        News news = newsFeed.getNewsByTitle(title);
        if (news == null) {
            System.out.println("News not found: " + title); // Debugging
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/news_details.fxml"));
            Parent root = loader.load();

            NewsDetailsController controller = loader.getController();
            controller.setNews(news);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("News Details");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void displayLeaderboard() {
        List<User> topUsers = leaderboardFetcher.getTopUsers();

        if (topUsers.size() >= 3) {
            firstPlaceButton.setText("1. " + topUsers.get(0).getName());
            secondPlaceButton.setText("2. " + topUsers.get(1).getName());
            thirdPlaceButton.setText("3. " + topUsers.get(2).getName());
        }
    }

    @FXML
    private void displayChallengeDetails(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        Pane graphicPane = (Pane) sourceButton.getGraphic();
        String challengeTitle = ((Label) graphicPane.getChildren().get(0)).getText().trim();

        // Log judul challenge
        System.out.println("Judul Challenge: " + challengeTitle);

        Challenge challenge = detailsFetcher.getChallengeDetails(challengeTitle);

        if (challenge != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/challenge_details.fxml"));
                Parent root = loader.load();

                ChallengeDetailsController controller = loader.getController();
                controller.loadChallengeDetails(challenge);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Challenge Details");
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace(); // Tambahkan log untuk exception
                showError("Error", "Failed to load challenge details.");
            }
        } else {
            showError("Error", "Challenge not found.");
        }
    }

    @FXML
    private void showUserDetails(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        String buttonText = sourceButton.getText();
        String userName = buttonText.split("\\. ")[1];

        User user = leaderboardFetcher.getUserByName(userName);
        if (user != null) {
            showAlert("User Details", user.getName() + " memiliki " + user.getTotalPoints() + " poin.");
        } else {
            showAlert("Error", "User tidak ditemukan.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Metode untuk membuka panel admin
    @FXML
    private void openAdminPanel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin_view.fxml"));
            Parent root = loader.load();

            AdminController adminController = loader.getController();
            adminController.setMainController(this); // Set MainController

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Admin Panel");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error", "Failed to load admin panel.");
        }
    }

    // Metode untuk memperbarui leaderboard
    public void updateLeaderboard() {
        List<User> topUsers = leaderboardFetcher.getTopUsers();

        if (topUsers.size() >= 3) {
            firstPlaceButton.setText("1. " + topUsers.get(0).getName());
            secondPlaceButton.setText("2. " + topUsers.get(1).getName());
            thirdPlaceButton.setText("3. " + topUsers.get(2).getName());
        }
    }
    @FXML
    public void showMarineSpecies() {
        try {
            AnchorPane marineSpeciesPane = FXMLLoader.load(getClass().getResource("/fxml/MarineSpecies.fxml"));
            contentPane.getChildren().setAll(marineSpeciesPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button profileButton;
    @FXML
    private Label usernameLabel;

    private User currentUser;

    public void setCurrentUser(User user) {
        this.currentUser = user;
        if (usernameLabel != null) {
            usernameLabel.setText(user.getName());
        }
    }

    @FXML
    private void showUserProfile() {
        if (currentUser != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Profil Pengguna");
            alert.setHeaderText("Informasi Pengguna");
            alert.setContentText("Nama: " + currentUser.getName() + "\nPoin: " + currentUser.getTotalPoints());
            alert.showAndWait();
        }
    }

    private void loadNewsFromDatabase() {
        try (Connection connection = DatabaseHelper.getConnection()) {
            String query = "SELECT * FROM news";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                News news = new News(
                        resultSet.getInt("id"),
                        resultSet.getInt("admin_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("image_url")
                );
                newsFeed.addNews(news);
                System.out.println("Loaded news: " + news.getTitle()); // Debugging
            }
        } catch (SQLException e) {
            System.err.println("Error fetching news: " + e.getMessage());
        }
    }

    @FXML
    private void showNewsDetails() {
        try {
            // Ambil berita dari database
            NewsDAO newsDAO = new NewsDAO();
            News news = newsDAO.getNewsById(1); // Ganti dengan ID yang sesuai

            // Load FXML untuk detail berita
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/news_details.fxml"));
            Parent root = loader.load();

            // Set berita di controller detail
            NewsDetailsController controller = loader.getController();
            controller.setNews(news);

            // Tampilkan popup
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("News Details");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
