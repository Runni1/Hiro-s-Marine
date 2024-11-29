package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RecommendationController {

    public void handleDiscussionButtonClick(ActionEvent event) {
        try {
            // Load FXML untuk stage baru
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DiscussionStage.fxml"));
            Parent root = loader.load();

            // Buat stage baru
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Diskusi Rekomendasi");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
