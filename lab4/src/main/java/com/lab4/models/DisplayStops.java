package com.lab4.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.lab4.validator.TripValidator;
import com.lab4.validator.Validator;

public class DisplayStops {

    private static Validator tripValidator = new TripValidator();

    public static void displayStopsForTrip(Connection conn, Scanner scanner) {
        int tripNumber = 0;
        while (true) {
            System.out.print("Enter Trip Number (4-digit): ");
            String input = scanner.nextLine();
            if (tripValidator.validate(input)) { // Regex to match 4-digit number
                tripNumber = Integer.parseInt(input);
                break;
            } else {
                System.out.println("Invalid Trip Number. Please enter a 4-digit number.");
            }
        }

        String sql = "SELECT StopNumber, SequenceNumber, DrivingTime FROM TripStopInfo WHERE TripNumber = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tripNumber);

            try (ResultSet rs = pstmt.executeQuery()) {
                boolean hasStops = false;
                while (rs.next()) {
                    if (!hasStops) {
                        System.out.println("Stops for Trip Number " + tripNumber + ":");
                        hasStops = true;
                    }
                    System.out.println("Stop Number: " + rs.getInt("StopNumber") +
                                       ", Sequence Number: " + rs.getInt("SequenceNumber") +
                                       ", Driving Time: " + rs.getString("DrivingTime"));
                }
                if (!hasStops) {
                    System.out.println("No stops found for Trip Number " + tripNumber);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error displaying the stops");
            e.printStackTrace();
        }
    }
}
