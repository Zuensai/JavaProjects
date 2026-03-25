package com.calculator.presentation;

import com.calculator.model.CalcResult;
import com.calculator.model.Operator;

import java.util.Scanner;

public class ConsoleUI implements iUserInterface {

    private final iCalcController controller;
    private final Scanner scanner;

    public ConsoleUI(iCalcController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void start() {
        System.out.println("Welcome to the Calculator!");

        boolean running = true;
        while (running) {
            System.out.println("\n --- MENU ---");
            System.out.println("1. Calculate");
            System.out.println("2. Quit");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    runCalculation();
                    break;
                case "2":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }

        scanner.close();
    }

    private void runCalculation() {
        double a = askForNumber("Enter first number (0 - 10000): ");
        Operator operator = askForOperator();
        double b = askForNumber("Enter second number (0 - 10000): ");

        CalcResult result = controller.handleInput(a, operator, b);

        if (result.isSuccess()) {
            System.out.println(result.getLabel()+ ": " + result.getResult());
        } else {
            System.out.println("Error: " + result.getErrorMessage());
        }
    }

    private double askForNumber(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, please try again.");
                // double.parsedouble converts string to a double
                // if user types letters, numberformatexception
                // catch the error and loop again, instead of crashing
                // while true keeps it running until valid input
                // same idea below
            }
        }
    }

    private Operator askForOperator() {
        while (true) {
            System.out.println("Enter operator (+, -, *, %, /): ");
            String input = scanner.nextLine();
            try {
                return Operator.fromSymbol(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid operator, please try again,");
            }
        }
    }
}
