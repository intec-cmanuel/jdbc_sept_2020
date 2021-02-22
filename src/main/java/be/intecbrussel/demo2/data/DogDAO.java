package be.intecbrussel.demo2.data;

import be.intecbrussel.demo2.model.Dog;
import be.intecbrussel.demo2.model.User;

import java.sql.*;

public class DogDAO {
    private final String url = "jdbc:mysql://localhost:3306/intec_test_persondb";
    private String username;
    private String password;

    public void saveDog(Dog dog, String ownerName) throws SQLException {
        String query = "INSERT INTO dog (name, owner) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, dog.getName());
            statement.setString(2, ownerName);

            statement.execute();

        }
    }

    public Dog getDog(User user) throws SQLException{
        String query = "SELECT * FROM dog WHERE owner LIKE ?";
        Dog dog = null;
        try (Connection connection = DriverManager.getConnection(url, this.username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getUsername());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                dog = new Dog(name);
            }

        }
        return dog;

    }

    public void setConfig(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
