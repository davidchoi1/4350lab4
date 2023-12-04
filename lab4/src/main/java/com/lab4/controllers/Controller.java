package com.lab4.controllers;

import java.sql.Connection;
import java.util.Scanner;

import com.lab4.menuoptions.MenuOptions;
import com.lab4.models.AddBus;
import com.lab4.models.AddDriver;
import com.lab4.models.DisplayStops;
import com.lab4.models.DisplayWeeklySchedule;
import com.lab4.models.EditSchedule;
import com.lab4.models.RecordActualTripStopInfo;
import com.lab4.models.RemoveBus;
import com.lab4.models.ScheduleDisplay;

public class Controller {

    // Method to execute functionality based on the user's choice
    public static void executeFunctionality(int choice, Connection conn, Scanner scanner) {
        switch (choice) {
            case 1:
                ScheduleDisplay.displaySchedule(conn, scanner);
                break;
            case 2:
                int userChoice = MenuOptions.editScheduleMenu(scanner);
                executeEditFunctionality(userChoice, conn, scanner);
                break;
            case 3:
                DisplayStops.displayStopsForTrip(conn, scanner);
                break;
            case 4:
                DisplayWeeklySchedule.displayWeeklySchedule(conn, scanner);
                break;
            case 5:
                AddDriver.addNewDriver(conn, scanner);
                break;
            case 6:
                AddBus.addNewBus(conn, scanner);
                break;
            case 7:
                RemoveBus.removeBus(conn, scanner);
                break;
            case 8:
                // Assuming Additional  Functionality is a class with a method performAction()
                RecordActualTripStopInfo.recordActualData(conn, scanner);
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 8.");
                break;
        }
    }

    public static void executeEditFunctionality(int choice, Connection conn, Scanner scanner) {
        switch (choice) {
            case 1:
                EditSchedule.deleteTripOffering(conn, scanner);
                break;
            case 2:
                EditSchedule.addTripOfferings(conn, scanner);
                break;
            case 3:
                EditSchedule.changeDriver(conn, scanner);
                break;
            case 4:
                EditSchedule.changeBus(conn, scanner);
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 8.");
                break;     
        }
    }

}
