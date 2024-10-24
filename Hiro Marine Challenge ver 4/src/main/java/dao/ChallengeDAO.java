package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.Challenge;

public class ChallengeDAO extends BaseDAO {
    private static final Logger LOGGER = Logger.getLogger(ChallengeDAO.class.getName());

    // Membuat objek Challenge dari ResultSet
    private Challenge createChallengeFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        // Inisialisasi Challenge dengan data dari ResultSet
        Challenge challenge = new Challenge(id, name, description, 0, null, null);
        return challenge;
    }

    // Mendapatkan Challenge berdasarkan ID
    public Challenge getChallengeById(int id) {
        if (id <= 0) {
            LOGGER.log(Level.WARNING, "Invalid ID provided: " + id);
            return null;
        }

        String query = "SELECT * FROM challenges WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Mengembalikan Challenge jika ditemukan
                    return createChallengeFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching challenge by ID: " + e.getMessage(), e);
        }
        return null;
    }

    // Tambahkan metode CRUD lainnya sesuai kebutuhan
}
