package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.DatabaseHelper;
import models.User;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleLogin() {
        // Pastikan mainController tidak null sebelum digunakan
        if (mainController == null) {
            System.out.println("MainController is not set!");
            return;
        }

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Error", "Nama pengguna dan sandi tidak boleh kosong.");
            return;
        }

        try (Connection connection = DatabaseHelper.getConnection()) {
            String query = "SELECT * FROM users WHERE name = ? AND user_password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User loggedInUser = new User(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("total_points")
                );
                mainController.setCurrentUser(loggedInUser);
                showAlert("Success", "Login berhasil!");
                goToMain();
            } else {
                showError("Error", "Nama pengguna atau sandi salah.");
            }
        } catch (SQLException e) {
            showError("Error", "Terjadi kesalahan saat login: " + e.getMessage());
        }
    }

    @FXML
    private void goToRegister() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/register.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            showError("Error", "Gagal memuat tampilan registrasi.");
        }
    }

    private void goToMain() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            showError("Error", "Gagal memuat tampilan utama.");
        }
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
