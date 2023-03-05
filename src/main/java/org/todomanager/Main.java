package org.todomanager;

import org.todomanager.service.UserManagement;
import org.todomanager.service.UserOpertaion;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("\n----------------------------------------------------- Welcome to Todo Manager! ---------------------------------------------------------\n");

        Scanner sc = new Scanner(System.in);
        boolean mainMenu = true;
        int choice;
        String username, email, password;

        while(mainMenu)
        {
            UserManagement user = new UserOpertaion();
            System.out.println("\nPlease Select\n" +
                    "1. Login\n" +
                    "2. Register\n" +
                    "3. Log Off");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.println("Enter the Email");
                    email = sc.next();
                    System.out.println("Enter the Password");
                    password = sc.next();
                    user.login(email, password);
                }
                case 2 -> {
                    System.out.println("Enter the UserName");
                    username = sc.next();
                    System.out.println("Enter the Email");
                    email = sc.next();
                    System.out.println("Enter the password");
                    password = sc.next();
                    user.register(username, email, password);
                }
                case 3 -> {
                    user.logout();
                    mainMenu = false;
                }
                default -> System.out.println("Please Select Correct Option!");
            }
        }
    }
}
