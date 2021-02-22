package be.intecbrussel.demo2.data;

import be.intecbrussel.demo1.model.Human;
import be.intecbrussel.demo2.model.User;

import java.sql.*;

public class UserDAO {
    private final String url = "jdbc:mysql://localhost:3306/intec_test_persondb";
    private String user;
    private String password;

    public void saveUser(User user) {
        String query = "INSERT INTO user (username, password) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, this.user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.execute();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    public User getUser(String username) throws SQLException {
        String query = "SELECT * FROM user WHERE username LIKE ?";
        User user = null;

        try (Connection connection = DriverManager.getConnection(url, this.user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();
            rs.next();
            String usernameFromDB = rs.getString("username");
            String password = rs.getString("password");

            user = new User(usernameFromDB, password);
        }

        return user;
    }

    public void setConfig(String user, String password) {
        this.user = user;
        this.password = password;
    }
}
