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

public class RegisterController {

    @FXML
    private TextField newUsernameField;
    @FXML
    private PasswordField newPasswordField;

    @FXML
    private void handleRegister() {
        String username = newUsernameField.getText();
        String password = newPasswordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Error", "Nama pengguna dan sandi tidak boleh kosong.");
            return;
        }

        try (Connection connection = DatabaseHelper.getConnection()) {
            // Periksa apakah username sudah ada
            String checkQuery = "SELECT * FROM users WHERE name = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, username);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                showError("Error", "Nama pengguna sudah ada.");
                return;
            }

            // Masukkan pengguna baru ke database
            String insertQuery = "INSERT INTO users (name, user_password, total_points) VALUES (?, ?, 0)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                showAlert("Success", "Registrasi berhasil!");
                goToLogin();
            } else {
                showError("Error", "Registrasi gagal.");
            }
        } catch (SQLException e) {
            showError("Error", "Terjadi kesalahan saat registrasi: " + e.getMessage());
        }
    }

    @FXML
    private void goToLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
            Stage stage = (Stage) newUsernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            showError("Error", "Gagal memuat tampilan login.");
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
