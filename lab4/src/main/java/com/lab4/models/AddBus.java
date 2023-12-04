package com.lab4.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import com.lab4.validator.BusValidator;
import com.lab4.validator.Validator;

public class AddBus {
    private static Validator busValidator = new BusValidator();

    public static void addNewBus(Connection conn, Scanner scanner) {

        String busID;
        while (true) {
            System.out.print("Enter Bus ID (4 chars starting with B): ");
            busID = scanner.nextLine();
            if (busValidator.validate(busID)) {
                break;
            } else {
                System.out.println("Invalid BusID, must be 4 chars starting with B.");
            }
        }


        System.out.print("Enter Model: ");
        String model = scanner.nextLine();

        int year = 0;
        while (true) {
            System.out.print("Enter Year (e.g., 2020): ");
            String yearInput = scanner.nextLine();
            try {
                year = Integer.parseInt(yearInput);
                if (yearInput.length() == 4 &&  year > 1900 && year <= java.time.Year.now().getValue()) {
                    break; // Year is valid
                } else {
                    System.out.println("Invalid year. Please enter a valid year.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        insertBus(conn, busID, model, year);
    }

    private static void insertBus(Connection conn, String busID, String model, int year) {
        String sql = "INSERT INTO Bus (BusID, Model, TheYear) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, busID);
            pstmt.setString(2, model);
            pstmt.setInt(3, year);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Bus successfully added.");
            } else {
                System.out.println("Bus could not be added.");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting the bus into the database");
            e.printStackTrace();
        }
    }

}
