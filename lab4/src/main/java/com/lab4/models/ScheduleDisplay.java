package com.lab4.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.lab4.validator.DateValidator;
import com.lab4.validator.Validator;

public class ScheduleDisplay {
    private static Validator dateValidator = new DateValidator();

    public static void displaySchedule(Connection conn, Scanner scanner) {
        System.out.print("Enter Start Location Name: ");
        String startLocationName = scanner.nextLine();

        System.out.print("Enter Destination Name: ");
        String destinationName = scanner.nextLine();

        String dateString;
        while (true) {
            System.out.print("Enter Date (YYYY-MM-DD): ");
            dateString = scanner.nextLine();
            if (dateValidator.validate(dateString)) {
                break;
            } else {
                System.out.println("Wrong date format. Please try (YYYY-MM-DD)");
            }
        }

        String sql = "SELECT ScheduledStartTime, ScheduledArrivalTime, DriverName, BusID " +
                "FROM TripOffering " +
                "JOIN Trip ON TripOffering.TripNumber = Trip.TripNumber " +
                "WHERE StartLocationName = ? AND DestinationName = ? AND TheDate = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, startLocationName);
            pstmt.setString(2, destinationName);
            pstmt.setString(3, dateString);

            try (ResultSet rs = pstmt.executeQuery()) {
                boolean hasTrips = false;
                while (rs.next()) {
                    if (!hasTrips) {
                        System.out.println("Schedule for trips from " + startLocationName + " to " + destinationName
                                + " on " + dateString + ":");
                        hasTrips = true;
                    }
                    System.out.println("Scheduled Start Time: " + rs.getString("ScheduledStartTime") +
                            ", Scheduled Arrival Time: " + rs.getString("ScheduledArrivalTime") +
                            ", Driver Name: " + rs.getString("DriverName") +
                            ", Bus ID: " + rs.getString("BusID"));
                }
                if (!hasTrips) {
                    System.out.println("No trips found for the given criteria.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error displaying the schedule");
            e.printStackTrace();
        }
    }
}
