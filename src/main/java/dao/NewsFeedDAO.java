package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.News;

public class NewsFeedDAO extends BaseDAO {
    private static final Logger LOGGER = Logger.getLogger(NewsFeedDAO.class.getName());

    public List<News> getAllNews() {
        List<News> newsList = new ArrayList<>();
        String query = "SELECT * FROM news";
        
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                News news = new News(
                    rs.getInt("id"),
                    rs.getInt("admin_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("image_url")
                );
                newsList.add(news);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching news feed: " + e.getMessage(), e);
        }
        return newsList;
    }

    
}
