// File: /src/models/User.java
package models;

public class User {
    private int id;
    private String name;
    private int totalPoints;

    public User(int id, String name, int totalPoints) {
        this.id = id;
        this.name = name;
        this.totalPoints = totalPoints;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getTotalPoints() { return totalPoints; }

    @Override
    public String toString() {
        return name; // Tampilkan nama pengguna
    }
}
