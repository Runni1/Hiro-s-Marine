// File: /src/models/Challenge.java
//nambahin adfatar challenge yang telah diselesaikan user. challenge dapat menampung user mana saja yang telah diselesaikan user
package models;

public class Challenge {
    private int id;
    private String title;
    private String description;
    private int points;
    private String imageUrl;
    private String qrCodeUrl;

    public Challenge(int id, String title, String description, int points, String imageUrl, String qrCodeUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.points = points;
        this.imageUrl = imageUrl;
        this.qrCodeUrl = qrCodeUrl;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getPoints() { return points; }
    public String getImageUrl() { return imageUrl; }
    public String getQrCodeUrl() { return qrCodeUrl; }

    @Override
    public String toString() {
        return title;
    }
}
