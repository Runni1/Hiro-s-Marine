package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.User;

public class UserDAO extends BaseDAO {
    private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());

    // Mendapatkan User berdasarkan ID
    public User getUserById(int id) {
        User user = null;
        String query = "SELECT * FROM users WHERE id = ?";
        
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
             
            // Mengatur parameter ID dalam query
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Membuat objek User dari ResultSet
                    user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("totalPoints")
                    );
                }
            }
        } catch (SQLException e) {
            // Log kesalahan jika terjadi masalah saat mengambil data user
            LOGGER.log(Level.SEVERE, "Error fetching user: " + e.getMessage(), e);
        }
        return user;
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        User user = null;
        String query = "SELECT * FROM users WHERE name = ? AND user_password = ?";

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("total_points")
                    );
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching user: " + e.getMessage(), e);
        }
        return user;
    }
}
