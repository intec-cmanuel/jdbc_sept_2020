package be.intecbrussel.demo2.view;

import be.intecbrussel.demo2.data.DogDAO;
import be.intecbrussel.demo2.data.UserDAO;
import be.intecbrussel.demo2.model.Dog;
import be.intecbrussel.demo2.model.DogShelter;
import be.intecbrussel.demo2.model.User;
import be.intecbrussel.demo2.util.UserValidator;

import java.sql.SQLException;
import java.util.Scanner;

public class CuiView {
    private DogDAO dogDao = new DogDAO();
    private UserDAO userDao = new UserDAO();

    public void start(){
        Scanner scanner = new Scanner(System.in);

        logInToDatabase(scanner);
        User user = logInProcess(scanner);
        Dog dog = adoptionProcess(scanner, user);

        dog.pet();

        scanner.close();
    }

    private User logInProcess(Scanner scanner) {
        System.out.println("Login (1) or Register (2) ?");
        int logInChoice = Integer.parseInt(scanner.nextLine());
        User user = null;
        if (logInChoice == 1) {
            user = logIn(scanner);
        } else if (logInChoice == 2) {
            user = register(scanner);
        } else {
            System.out.println("Invalid");
        }

        return user;
    }

    private User register(Scanner scanner) {
        System.out.println("Please Register as a user.");

        System.out.println("Enter user username :");
        String username = scanner.nextLine();

        System.out.println("Enter user password :");
        String password = scanner.nextLine();

        User user = new User(username, password);
        userDao.saveUser(user);
        return user;
    }

    private User logIn(Scanner scanner) {
        System.out.println("Please log in as a user.");

        System.out.println("Enter user username :");
        String username = scanner.nextLine();

        System.out.println("Enter user password :");
        String password = scanner.nextLine();

        User user = null;
        try {
            user = userDao.getUser(username);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (UserValidator.validateUserPassword(user, password)) {
             return user;
         } else {
             return null;
         }
    }

    private Dog adoptionProcess(Scanner scanner, User user) {
        System.out.println("Do you want to adopt a dog (1) or get your dog from DB (2)");
        int dogChoice = Integer.parseInt(scanner.nextLine());
        Dog dog = null;
        if (dogChoice == 1) {
            DogShelter dogShelter = new DogShelter();
            System.out.println("Name of the dog you want to Adopt");
            dog = dogShelter.adopt(scanner.nextLine());
            try {
                dogDao.saveDog(dog, user.getUsername());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (dogChoice == 2) {
            try {
                dog = dogDao.getDog(user);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            System.out.println("Invalid");
        }

        return dog;
    }

    private void logInToDatabase(Scanner scanner) {
        System.out.println("Please log in to the database.");

        System.out.println("Enter database username :");
        String username = scanner.nextLine();

        System.out.println("Enter database password :");
        String password = scanner.nextLine();

        dogDao.setConfig(username, password);
        userDao.setConfig(username, password);
    }

}
