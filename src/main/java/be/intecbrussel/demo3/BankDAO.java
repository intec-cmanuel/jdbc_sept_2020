package be.intecbrussel.demo3;

import be.intecbrussel.demo2.model.User;

import java.sql.*;
import java.util.Scanner;

public class BankDAO {
    private final String url = "jdbc:mysql://localhost:3306/intec_test_persondb";
    private String user;
    private String password;

    public void transferMoney (BankUser source, BankUser target, double amount) throws SQLException {

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            connection.setAutoCommit(false);
            String query = "UPDATE bankuser SET money = ? WHERE name LIKE ?";

            // STEP 1: Remove money from source
            PreparedStatement statementToRemove = connection.prepareStatement(query);
            statementToRemove.setString(2, source.getName());
            statementToRemove.setDouble(1, source.getMoney() - amount);
            int removingMoney = statementToRemove.executeUpdate();
            statementToRemove.close();

            new Scanner(System.in).nextLine();

            // STEP 2: Add money to target
            PreparedStatement statementToAdd = connection.prepareStatement(query);
            statementToAdd.setString(2, target.getName());
            statementToAdd.setDouble(1, target.getMoney() + amount);
            int addingMoney = statementToAdd.executeUpdate();
            statementToAdd.close();

            if (removingMoney == 1 && addingMoney == 1) {
                connection.commit();
                source.setMoney(source.getMoney() - amount);
                target.setMoney(target.getMoney() + amount);
            } else {
                connection.rollback();
            }

        }
    }

    public void registerUser (BankUser user) {
        String query = "INSERT INTO bankuser (name, money) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, this.user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getName());
            statement.setDouble(2, user.getMoney());
            statement.execute();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public BankUser getUser (String name) throws SQLException {
        String query = "SELECT * FROM bankuser WHERE name LIKE ?";
        BankUser user = null;

        try (Connection connection = DriverManager.getConnection(url, this.user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);

            ResultSet rs = statement.executeQuery();
            rs.next();
            String usernameFromDB = rs.getString("name");
            double money = rs.getDouble("money");

            user = new BankUser(usernameFromDB, money);
        }

        return user;
    }

    public void setConfig(String user, String password) {
        this.user = user;
        this.password = password;
    }
}
