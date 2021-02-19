package be.intecbrussel.demo2.util;

public class UserValidator {
    public static boolean validateUserPassword(User user, String password) {
        return user.getPassword().equals(password);
    }
}
