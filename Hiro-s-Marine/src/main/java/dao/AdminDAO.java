package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.Admin;
import utils.DatabaseHelper;

public class AdminDAO {
    public Admin getAdminByUsernameAndPassword(String username, String password) {
        try (Connection connection = DatabaseHelper.getConnection()) {
            String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Admin(
                    resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
} 