package com.lab4.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import com.lab4.validator.BusValidator;
import com.lab4.validator.Validator;

public class RemoveBus {

    private static Validator busValidator = new BusValidator();

    public static void removeBus(Connection conn, Scanner scanner) {
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
        
        String sql = "DELETE FROM Bus WHERE BusID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, busID);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Bus successfully removed.");
            } else {
                System.out.println("Bus could not be found or removed.");
            }
        } catch (SQLException e) {
            System.out.println("Error removing the bus from the database");
            e.printStackTrace();
        }
    }
}

