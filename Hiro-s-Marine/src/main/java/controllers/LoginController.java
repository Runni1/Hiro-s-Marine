package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.AdminDAO;
import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.DatabaseHelper;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private MainController mainController;
    private AdminDAO adminDAO = new AdminDAO();
    private UserDAO userDAO = new UserDAO();

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (mainController == null) {
            showError("Initialization Error", "MainController is not initialized.");
            return;
        }

        if (adminDAO.getAdminByUsernameAndPassword(username, password) != null) {
            System.out.println("Admin login successful");
            mainController.setAdmin(true);
            goToMain();
        } else if (userDAO.getUserByUsernameAndPassword(username, password) != null) {
            System.out.println("User login successful");
            mainController.setAdmin(false);
            goToMain();
        } else {
            showError("Login Failed", "Invalid username or password.");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
            Parent root = loader.load();
            MainController loadedMainController = loader.getController();
            loadedMainController.setAdmin(mainController.isAdmin());
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

    private boolean isAdmin(String username, String password) {
        try (Connection connection = DatabaseHelper.getConnection()) {
            String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            showError("Error", "Terjadi kesalahan saat memeriksa admin: " + e.getMessage());
            return false;
        }
    }
}