package be.intecbrussel.demo2.data;

import be.intecbrussel.demo2.model.Dog;
import be.intecbrussel.demo2.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DogDAO {
    private final String url = "jdbc:mysql://localhost:3306/intec_test_persondb";
    private String username;
    private String password;

    public void saveDog(Dog dog, String ownerName) throws SQLException {
        String query = "INSERT INTO dog (name, ownerName) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, dog.getName());
            statement.setString(2, ownerName);

            statement.execute();

        }
    }

    public Dog getDog(User user) {
        return null;
    }

    public void setConfig(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
