package be.intecbrussel.demo3;

import java.sql.SQLException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        BankDAO bdao = new BankDAO();
        bdao.setConfig(scanner.nextLine(), scanner.nextLine());


        BankUser audric = bdao.getUser("Audric");
        BankUser anthony = bdao.getUser("Anthony");
        bdao.transferMoney(audric, anthony, 2000);

        System.out.println(audric);
        System.out.println(anthony);

    }
}
