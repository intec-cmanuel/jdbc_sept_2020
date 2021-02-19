package be.intecbrussel.demo1.data;

import be.intecbrussel.demo1.model.Human;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HumanDAO {
    private String url;
    private String user;
    private String password;

    public void setConfig(String url, String username, String password){
        this.url = url;
        this.user = username;
        this.password = password;
    }

    public void saveHuman(Human human) throws SQLException {

        try (Connection connection = DriverManager.getConnection(url, user, password);
                Statement statement = connection.createStatement()) {

            String query = String.format("INSERT INTO human (name, lastname, gender) VALUES ('%s', '%s', '%s')",
                    human.getName(), human.getLastName(), human.getGender());

            statement.execute(query);

        }

    }

    public List<Human> getAllHumans() throws SQLException {
        List<Human> humanList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM human";

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("name");
                String lastName = rs.getString("lastname");
                String gender = rs.getString("gender");

                Human person = new Human(name, lastName, gender);
                humanList.add(person);
            }

        }

        return humanList;
    }
}
