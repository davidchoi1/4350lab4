package com.lab4.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.lab4.validator.DateValidator;
import com.lab4.validator.Validator;

public class DisplayWeeklySchedule {

    private static Validator dateValidator = new DateValidator();

    public static void displayWeeklySchedule(Connection conn, Scanner scanner) {
        System.out.print("Enter Driver Name: ");
        String driverName = scanner.nextLine();

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


        String sql = "SELECT ScheduledStartTime, ScheduledArrivalTime FROM TripOffering WHERE DriverName = ? AND TheDate = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, driverName);
            pstmt.setString(2, dateString);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Scheduled Start Time: " + rs.getTime("ScheduledStartTime") +
                                       ", Scheduled Arrival Time: " + rs.getTime("ScheduledArrivalTime"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error displaying the weekly schedule");
            e.printStackTrace();
        }
    }
}
