package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.User;

public class LeaderboardFetcher {

    public List<User> getTopUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users ORDER BY total_points DESC LIMIT 3";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("total_points")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getUserByName(String name) {
        User user = null;
        String query = "SELECT id, name, total_points FROM users WHERE name = ?";

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("total_points")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error fetching user: " + e.getMessage());
        }

        return user;
    }

    public boolean verifyAndAddPoints(String userName, int points) {
        String query = "UPDATE users SET total_points = total_points + ? WHERE name = ?";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, points);
            preparedStatement.setString(2, userName);
            int rowsUpdated = preparedStatement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user points: " + e.getMessage());
            return false;
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT id, name, total_points FROM users";

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("total_points")
                );
                users.add(user);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }

        return users;
    }

    // Metode untuk memeriksa apakah tantangan sudah diverifikasi
    public boolean isChallengeVerified(int userId, int challengeId) {
        String query = "SELECT * FROM user_challenges WHERE user_id = ? AND challenge_id = ?";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, challengeId);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            System.err.println("Error checking challenge verification: " + e.getMessage());
            return false;
        }
    }

    // Metode untuk menandai tantangan sebagai terverifikasi
    public void markChallengeAsVerified(int userId, int challengeId) {
        String query = "INSERT INTO user_challenges (user_id, challenge_id) VALUES (?, ?)";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, challengeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error marking challenge as verified: " + e.getMessage());
        }
    }
}
