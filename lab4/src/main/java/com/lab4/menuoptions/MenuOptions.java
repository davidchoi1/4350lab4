package com.lab4.menuoptions;

import java.util.Scanner;

public class MenuOptions {

    public static int optionsMenu(Scanner scanner) {
        int choice = 0;

        while (true) { // Infinite loop that will run until a valid choice is made
            System.out.println("Please select an option:");
            System.out.println("1) Display schedule of trips given start location and destination");
            System.out.println("2) Edit schedule");
            System.out.println("3) Display stops of a given trip");
            System.out.println("4) Display weekly schedule of a given driver and date");
            System.out.println("5) Add a driver");
            System.out.println("6) Add a bus");
            System.out.println("7) Delete a bus");
            System.out.println("8) Insert actual data of a given Trip Offering");

            System.out.print("Enter your choice (1-8): ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > 8) {
                    System.out.println("Invalid choice. Please enter a number between 1 and 8.");
                } else {
                    break; // Exit the loop if choice is valid
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        return choice;

    }

    public static int editScheduleMenu(Scanner scanner) {
        int choice = 0;

        while (true) { // Infinite loop that will run until a valid choice is made
            System.out.println("Please select an option:");
            System.out.println("1) Delete trip offering specified by trip, date, and start time");
            System.out.println("2) Add a set of trip offerings");
            System.out.println("3) Change driver for a trip");
            System.out.println("4) Change bus for a trip");

            System.out.print("Enter your choice (1-4): ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > 4) {
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                } else {
                    break; // Exit the loop if choice is valid
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        return choice;
    }

}
