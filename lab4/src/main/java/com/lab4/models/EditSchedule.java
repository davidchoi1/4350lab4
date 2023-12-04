package com.lab4.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import com.lab4.validator.BusValidator;
import com.lab4.validator.DateValidator;
import com.lab4.validator.TimeValidator;
import com.lab4.validator.TripValidator;
import com.lab4.validator.Validator;

public class EditSchedule {

    private static Validator busValidator = new BusValidator();
    private static Validator dateValidator = new DateValidator();
    private static Validator timeValidator = new TimeValidator();
    private static Validator tripValidator = new TripValidator();

    public static void deleteTripOffering(Connection conn, Scanner scanner) {
        int tripNumber = 0;
        while (tripNumber == 0) {
            System.out.print("Enter Trip Number (4-digit): ");
            String input = scanner.nextLine();
            if (input.matches("\\d{4}")) { // Regex to match 4-digit number
                tripNumber = Integer.parseInt(input);
            } else {
                System.out.println("Invalid Trip Number. Please enter a 4-digit number.");
            }
        }

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

        String startTime;
        boolean validTime;
        do {
            System.out.print("Enter Scheduled Start Time (HH:MM:SS): ");
            startTime = scanner.nextLine();
            validTime = timeValidator.validate(startTime);
            if (!validTime) {
                System.out.println("Invalid time format. Please use HH:MM:SS (24-hour format).");
            }
        } while (!validTime);

        String sql = "DELETE FROM TripOffering WHERE TripNumber = ? AND TheDate = ? AND ScheduledStartTime = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tripNumber);
            pstmt.setString(2, dateString);
            pstmt.setString(3, startTime);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Trip offering successfully deleted.");
            } else {
                System.out.println("Trip offering not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting the trip offering");
            e.printStackTrace();
        }
    }

    public static void addTripOfferings(Connection conn, Scanner scanner) {
        String input = "";
        do {
            int tripNumber = 0;
            while (true) {
                System.out.print("Enter Trip Number (4-digit): ");
                String lnput = scanner.nextLine();
                if (tripValidator.validate(lnput)) { // Regex to match 4-digit number
                    tripNumber = Integer.parseInt(lnput);
                    break;
                } else {
                    System.out.println("Invalid Trip Number. Please enter a 4-digit number.");
                }
            }

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

            String startTime;
            boolean validTime;
            do {
                System.out.print("Enter Scheduled Start Time (HH:MM:SS): ");
                startTime = scanner.nextLine();
                validTime = timeValidator.validate(startTime);
                if (!validTime) {
                    System.out.println("Invalid time format. Please use HH:MM:SS (24-hour format).");
                }
            } while (!validTime);

            String arrivalTime;
            do {
                System.out.print("Enter Scheduled Arrival Time (HH:MM:SS): ");
                arrivalTime = scanner.nextLine();
                validTime = timeValidator.validate(startTime);
                if (!validTime) {
                    System.out.println("Invalid time format. Please use HH:MM:SS (24-hour format).");
                }
            } while (!validTime);

            System.out.print("Enter Driver Name: ");
            String driverName = scanner.nextLine();

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

            String sql = "INSERT INTO TripOffering (TripNumber, TheDate, ScheduledStartTime, ScheduledArrivalTime, DriverName, BusID) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, tripNumber);
                pstmt.setString(2, dateString);
                pstmt.setString(3, startTime);
                pstmt.setString(4, arrivalTime);
                pstmt.setString(5, driverName);
                pstmt.setString(6, busID);

                pstmt.executeUpdate();
                System.out.println("Trip offering successfully added.");
            } catch (SQLException e) {
                System.out.println("Error adding the trip offering");
                e.printStackTrace();
            }

            System.out.print("Do you want to add another trip offering? (yes/no): ");
            input = scanner.nextLine();
        } while (input.equalsIgnoreCase("yes"));
    }

    public static void changeDriver(Connection conn, Scanner scanner) {
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

        String startTime;
        boolean validTime;
        do {
            System.out.print("Enter Scheduled Start Time (HH:MM:SS): ");
            startTime = scanner.nextLine();
            validTime = timeValidator.validate(startTime);
            if (!validTime) {
                System.out.println("Invalid time format. Please use HH:MM:SS (24-hour format).");
            }
        } while (!validTime);

        System.out.print("Enter new Driver Name: ");
        String driverName = scanner.nextLine();

        String sql = "UPDATE TripOffering SET DriverName = ? WHERE TripNumber = ? AND TheDate = ? AND ScheduledStartTime = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, driverName);
            pstmt.setInt(2, tripNumber);
            pstmt.setString(3, dateString);
            pstmt.setString(4, startTime);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Driver successfully updated.");
            } else {
                System.out.println("Trip offering not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating the driver");
            e.printStackTrace();
        }
    }

    public static void changeBus(Connection conn, Scanner scanner) {
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

        String startTime;
        boolean validTime;
        do {
            System.out.print("Enter Scheduled Start Time (HH:MM:SS): ");
            startTime = scanner.nextLine();
            validTime = timeValidator.validate(startTime);
            if (!validTime) {
                System.out.println("Invalid time format. Please use HH:MM:SS (24-hour format).");
            }
        } while (!validTime);

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

        String sql = "UPDATE TripOffering SET BusID = ? WHERE TripNumber = ? AND TheDate = ? AND ScheduledStartTime = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, busID);
            pstmt.setInt(2, tripNumber);
            pstmt.setString(3, dateString);
            pstmt.setString(4, startTime);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Bus successfully updated.");
            } else {
                System.out.println("Trip offering not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating the bus");
            e.printStackTrace();
        }
    }
}
