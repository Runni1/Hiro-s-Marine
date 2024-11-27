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

        if (news.getImageUrl() != null && !news.getImageUrl().isEmpty()) {
            Image image = new Image(news.getImageUrl());
            newsImageView.setImage(image);
        } else {
            newsImageView.setImage(null);
        }
    }
}
