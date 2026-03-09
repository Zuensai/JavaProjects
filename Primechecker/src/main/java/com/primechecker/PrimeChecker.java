package com.primechecker;

public class PrimeChecker {
    public static void main(String [] args) {
        if (args.length == 0) { //catching no input
            System.out.println("ERROR: No input provided, please use numbers");
            return;
        }
        String input = args[0]; // "type" + "name" = "value"
        int number = 0;
        try {
            number = Integer.parseInt(input);
        } catch (NumberFormatException error) {
            System.out.println("ERROR: That's not a number!");
            return;
        }
        boolean result = isPrime(number);
        if (result) {
            System.out.println(number + " is a Prime Number!");
        } else {
            System.out.println(number + " is NOT a Prime Number");
        }
       }

    public static boolean isPrime(int number) { //methode true/false is boolean met input Int
            if (number < 2) return false;
            if (number == 2) return false; //more speed
            if (number % 2 == 0) return false; //more speed
            // for (int i = 2; i < number; i++) {
            for (int i = 3; i * i <= number; i+=2) { //more speed
                if (number % i == 0) return false;
            }
            return true;

    }
}
