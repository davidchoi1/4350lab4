package com.lab4.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AddDriver {

    public static void addNewDriver(Connection conn, Scanner scanner) {
        System.out.print("Enter Driver Name: ");
        String driverName = scanner.nextLine();
        String driverTelephoneNumber;
        while (true) {
            System.out.print("Enter Driver Telephone Number (10 digits): ");
            driverTelephoneNumber = scanner.nextLine();
            if(driverTelephoneNumber.length() == 10) {
                break;
            } else {
                System.out.println("Telephone number must be 10 digits");
            }
        }

        String sql = "INSERT INTO Driver (DriverName, DriverTelephoneNumber) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, driverName);
            pstmt.setString(2, driverTelephoneNumber);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Driver successfully added.");
            } else {
                System.out.println("Driver could not be added.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding the driver to the database");
            e.printStackTrace();
        }
    }
}
