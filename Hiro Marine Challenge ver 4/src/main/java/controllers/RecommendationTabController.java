package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RecommendationTabController {

    @FXML
    private HBox scrollPaneContent; // HBox di dalam ScrollPane pertama (fx:id="scrollPane")

    @FXML
    private VBox scrollPane2Content; // AnchorPane di dalam ScrollPane kedua (fx:id="scrollPane2")

    /**
     * Menambahkan card ke dalam ScrollPane pertama.
     *
     * @param card Card dalam bentuk AnchorPane.
     */
    public void addCardToScrollPane(AnchorPane card) {
        scrollPaneContent.getChildren().add(card);
    }

    /**
     * Menambahkan card ke dalam ScrollPane kedua.
     *
     * @param card Card dalam bentuk AnchorPane.
     */
    public void addCardToScrollPane2(AnchorPane card) {
        scrollPane2Content.getChildren().add(card);
    }
}
