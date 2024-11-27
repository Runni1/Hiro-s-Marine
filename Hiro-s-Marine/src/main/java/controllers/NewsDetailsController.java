package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.News;

public class NewsDetailsController {

    @FXML
    private Label titleLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label adminLabel;
    @FXML
    private ImageView newsImageView;

    public void setNews(News news) {
        titleLabel.setText(news.getTitle());
        descriptionLabel.setText(news.getDescription());
        adminLabel.setText("Admin ID: " + news.getAdminId());

        String imageUrl = news.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty() && (imageUrl.startsWith("http://") || imageUrl.startsWith("https://"))) {
            try {
                Image image = new Image(imageUrl);
                newsImageView.setImage(image);
            } catch (Exception e) {
                System.out.println("Invalid image URL: " + imageUrl);
                newsImageView.setImage(null);
            }
        } else {
            System.out.println("No valid image URL provided.");
            newsImageView.setImage(null);
        }
    }
}
