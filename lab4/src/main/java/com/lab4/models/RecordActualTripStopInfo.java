package com.lab4.models;

import java.sql.*;
import java.util.Scanner;

import com.lab4.validator.DateValidator;
import com.lab4.validator.TimeValidator;
import com.lab4.validator.Validator;

public class RecordActualTripStopInfo {

    private static Validator dateValidator = new DateValidator();
    private static Validator timeValidator = new TimeValidator();

    public static void recordActualData(Connection conn, Scanner scanner) {
        try {
            System.out.print("Enter Trip Number: ");
            int tripNumber = Integer.parseInt(scanner.nextLine());

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

            String scheduledStartTime;
            boolean validTime;
            do {
                System.out.print("Enter Scheduled Start Time (HH:MM:SS): ");
                scheduledStartTime = scanner.nextLine();
                validTime = timeValidator.validate(scheduledStartTime);
                if (!validTime) {
                    System.out.println("Invalid time format. Please use HH:MM:SS (24-hour format).");
                }
            } while (!validTime);

            // Retrieve data from TripStopInfo
            String query = "SELECT TripOffering.ScheduledArrivalTime, TripStopInfo.StopNumber " +
               "FROM TripOffering " +
               "JOIN TripStopInfo ON TripOffering.TripNumber = TripStopInfo.TripNumber " +
               "WHERE TripOffering.TripNumber = ? AND TripOffering.ScheduledStartTime = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, tripNumber);
                pstmt.setString(2, scheduledStartTime);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    int stopNumber = rs.getInt("StopNumber");
                    String scheduledArrivalTime = rs.getString("ScheduledArrivalTime");

                    String actualStartTime;
                    do {
                        System.out.print("Enter Actual Start Time (HH:MM:SS): ");
                        actualStartTime = scanner.nextLine();
                        validTime = timeValidator.validate(actualStartTime);
                        if (!validTime) {
                            System.out.println("Invalid time format. Please use HH:MM:SS (24-hour format).");
                        }
                    } while (!validTime);

                    String actualArrivalTime;
                    do {
                        System.out.print("Enter Actual Arrival Time (HH:MM:SS): ");
                        actualArrivalTime = scanner.nextLine();
                        validTime = timeValidator.validate(actualArrivalTime);
                        if (!validTime) {
                            System.out.println("Invalid time format. Please use HH:MM:SS (24-hour format).");
                        }
                    } while (!validTime);

                    System.out.print("Enter Number of Passengers In: ");
                    int numPassengersIn = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter Number of Passengers Out: ");
                    int numPassengersOut = Integer.parseInt(scanner.nextLine());

                    // Insert data into ActualTripStopInfo
                    String insertSql = "INSERT INTO ActualTripStopInfo (TripNumber, TheDate, ScheduledStartTime, StopNumber, ScheduledArrivalTime, ActualStartTime, ActualArrivalTime, NumberOfPassengerIn, NumberOfPassengerOut) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setInt(1, tripNumber);
                        insertStmt.setString(2, dateString);
                        insertStmt.setString(3, scheduledStartTime);
                        insertStmt.setInt(4, stopNumber);
                        insertStmt.setString(5, scheduledArrivalTime);
                        insertStmt.setString(6, actualStartTime);
                        insertStmt.setString(7, actualArrivalTime);
                        insertStmt.setInt(8, numPassengersIn);
                        insertStmt.setInt(9, numPassengersOut);

                        int affectedRows = insertStmt.executeUpdate();
                        if (affectedRows > 0) {
                            System.out.println("Actual trip stop info successfully recorded.");
                        } else {
                            System.out.println("Failed to record actual trip stop info.");
                        }
                    }
                } else {
                    System.out.println("No matching trip stop info found.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error occurred.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        }
    }
}
