package be.intecbrussel.demo1.view;

import be.intecbrussel.demo1.data.HumanDAO;
import be.intecbrussel.demo1.model.Human;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
    private static final HumanDAO humanDAO = new HumanDAO();

    public static void displayMainMenu()  {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the human app, where you might save humans!");
        System.out.println("You will now be asked to input data of a human! \n");

        System.out.println("What is its name?");
        String name = scanner.nextLine();

        System.out.println("What is its last name?");
        String lastName = scanner.nextLine();

        System.out.println("What is its gender?");
        String gender = scanner.nextLine();

        Human human = new Human(name, lastName, gender);

        try {
            humanDAO.saveHuman(human);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        scanner.close();
    }

    public static void displayAllExistingHumans() {
        try {
            List<Human> humanList = humanDAO.getAllHumans();
            humanList.forEach(System.out::println);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void logIn() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please log in.");

        System.out.println("Enter URL :");
        String url = scanner.nextLine();

        System.out.println("Enter port :");
        String port = scanner.nextLine();

        System.out.println("Enter database :");
        String db = scanner.nextLine();

        System.out.println("Enter username :");
        String username = scanner.nextLine();

        System.out.println("Enter password :");
        String password = scanner.nextLine();

        humanDAO.setConfig(String.format("jdbc:mysql://%s:%s/%s", url, port, db), username, password);
    }
}
