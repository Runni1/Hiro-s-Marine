package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.News;

public class NewsDAO extends BaseDAO {
    private static final Logger LOGGER = Logger.getLogger(NewsDAO.class.getName());

    public News getNewsById(int id) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        News news = null;
        try {
            con = getConnection(); // Pastikan metode ini ada dan berfungsi
            String query = "SELECT * FROM news WHERE id = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                news = new News(rs.getInt("id"), rs.getInt("adminId"), rs.getString("title"), rs.getString("description"), rs.getString("imageUrl"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching news by ID: " + e.getMessage(), e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources: " + e.getMessage(), e);
            }
        }
        return news;
    }

    public News getNewsByTitle(String title) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        News news = null;
        try {
            con = getConnection();
            String query = "SELECT * FROM news WHERE title = ?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, title);
            rs = stmt.executeQuery();
            if (rs.next()) {
                news = new News(rs.getInt("id"), rs.getInt("adminId"), rs.getString("title"), rs.getString("description"), rs.getString("imageUrl"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching news by title: " + e.getMessage(), e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources: " + e.getMessage(), e);
            }
        }
        return news;
    }

    
}
