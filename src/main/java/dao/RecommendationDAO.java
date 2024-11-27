package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Lokasi;
import models.Recommendation;

public class RecommendationDAO {

    private Connection connection;

    public RecommendationDAO(Connection connection) {
        this.connection = connection;
    }

    public void addRecommendation(Recommendation recommendation) throws SQLException {
        String query = "INSERT INTO recommendations (namaLokasi, deskripsi, gambar) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, recommendation.getNamaLokasi());
            statement.setString(2, recommendation.getDeskripsi());
            statement.setString(3, recommendation.getGambar());
            statement.executeUpdate();
        } catch (SQLException e) {
            // Tambahkan logging atau penanganan kesalahan
            throw e;
        }
    }

    public List<Recommendation> getAllRecommendations() {
        List<Recommendation> recommendations = new ArrayList<>();
        String query = "SELECT * FROM recommendations";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Recommendation recommendation = new Recommendation(
                    rs.getInt("id"),
                    rs.getString("namaLokasi"),
                    rs.getString("deskripsi"),
                    rs.getString("gambar"),
                    new ArrayList<>() // Assuming comments are loaded separately
                );
                recommendations.add(recommendation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recommendations;
    }

    public void updateRecommendation(Lokasi lokasi) throws SQLException {
        String query = "UPDATE Lokasi SET deskripsi = ?, gambar = ? WHERE namaLokasi = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, lokasi.getDeskripsi());
            statement.setString(2, lokasi.getGambar());
            statement.setString(3, lokasi.getNamaLokasi());
            statement.executeUpdate();
        } catch (SQLException e) {
            // Tambahkan logging atau penanganan kesalahan
            throw e;
        }
    }

    public void deleteRecommendation(String namaLokasi) throws SQLException {
        String query = "DELETE FROM Lokasi WHERE namaLokasi = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, namaLokasi);
            statement.executeUpdate();
        } catch (SQLException e) {
            // Tambahkan logging atau penanganan kesalahan
            throw e;
        }
    }
}
