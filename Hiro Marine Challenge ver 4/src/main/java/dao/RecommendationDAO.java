package dao;

import models.Recommendation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecommendationDAO {

    private Connection connection;

    public RecommendationDAO(Connection connection) {
        this.connection = connection;
    }

    public void addRecommendation(Recommendation.Lokasi lokasi) throws SQLException {
        String query = "INSERT INTO Lokasi (namaLokasi, deskripsi, gambar) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, lokasi.getNamaLokasi());
            statement.setString(2, lokasi.getDeskripsi());
            statement.setString(3, lokasi.getGambar());
            statement.executeUpdate();
        } catch (SQLException e) {
            // Tambahkan logging atau penanganan kesalahan
            throw e;
        }
    }

    public List<Recommendation.Lokasi> getAllRecommendations() throws SQLException {
        List<Recommendation.Lokasi> lokasiList = new ArrayList<>();
        String query = "SELECT * FROM Lokasi";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String namaLokasi = resultSet.getString("namaLokasi");
                String deskripsi = resultSet.getString("deskripsi");
                String gambar = resultSet.getString("gambar");
                lokasiList.add(new Recommendation().new Lokasi(namaLokasi, deskripsi, gambar));
            }
        } catch (SQLException e) {
            // Tambahkan logging atau penanganan kesalahan
            throw e;
        }
        return lokasiList;
    }

    public void updateRecommendation(Recommendation.Lokasi lokasi) throws SQLException {
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
