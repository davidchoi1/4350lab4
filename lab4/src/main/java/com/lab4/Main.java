package com.lab4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.lab4.controllers.Controller;
import com.lab4.menuoptions.MenuOptions;

public class Main {

    private static final String URL = "jdbc:mysql://localhost:3306/lab4"; // Database URL
    private static final String USER = "root"; // Database username
    private static final String PASSWORD = "Teddyboy?ehm8ii"; // Database password
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

            if (conn != null) {
                System.out.println("Connected to the database!");

                do {
                    int userChoice = MenuOptions.optionsMenu(scanner);
                    Controller.executeFunctionality(userChoice, conn, scanner);
                } while (continueProgram());

            }

        } catch (SQLException e) {
            System.out.println("Cannot connect to the database!");
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }


    public static boolean continueProgram() {
        String choice;

        while (true) {
            System.out.println("Would you like to do something else?");
            System.out.print("Enter your choice (y/n): ");
            choice = scanner.nextLine().toLowerCase();

            if (choice.equals("y")) {
                return true;  // If the user enters "y", return true
            } else if (choice.equals("n")) {
                return false;  // If the user enters "n", return false
            } else {
                System.out.println("Invalid choice, please enter 'y' for yes or 'n' for no.");
                // The loop will continue if the input is not "y" or "n"
            }
        }

    }

}
