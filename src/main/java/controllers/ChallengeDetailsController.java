// File: /src/controllers/ChallengeDetailsController.java
package controllers;

import java.io.File;
import java.io.IOException;

import com.google.zxing.WriterException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Challenge;
import utils.QRCodeGenerator;

public class ChallengeDetailsController {

    @FXML
    private ImageView challengeImage;
    @FXML
    private Label challengeTitle;
    @FXML
    private Text challengeDescription;
    @FXML
    private Label challengePoints;
    @FXML
    private ImageView qrCodeImage;
    @FXML
    private Button closeButton;

    public void loadChallengeDetails(Challenge challenge) {
        challengeTitle.setText(challenge.getTitle());
        challengeDescription.setText(challenge.getDescription());
        challengePoints.setText("Poin yang dapat diraih: " + challenge.getPoints());

        // Set challenge image
        try {
            challengeImage.setImage(new Image(new File(challenge.getImageUrl()).toURI().toString()));
        } catch (Exception e) {
            showError("Failed to load image", "Image not found or broken.");
        }

        // Generate and set the QR code
        try {
            String qrCodePath = "qrcodes/" + challenge.getId() + ".png";
            QRCodeGenerator.generateQRCodeImage(challenge.getQrCodeUrl(), 150, 150, qrCodePath);
            qrCodeImage.setImage(new Image(new File(qrCodePath).toURI().toString()));
        } catch (WriterException | IOException e) {
            showError("QR Code generation failed", e.getMessage());
        }
    }

    @FXML
    private void closeChallengeDetails() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
