package models;

public class Comment {
    private int id;
    private int recommendationId;
    private int userId;
    private String commentText;

    public Comment(int id, int recommendationId, int userId, String commentText) {
        this.id = id;
        this.recommendationId = recommendationId;
        this.userId = userId;
        this.commentText = commentText;
    }

    // Getters dan Setters
}
