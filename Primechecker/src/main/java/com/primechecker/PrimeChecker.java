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

        // System.out.println("Input: " + number);
        // System.out.println("Result " + result);
        
        if (result) {
            System.out.println(number + " is a Prime Number!");
        } else {
            System.out.println(number + " is NOT a Prime Number");
        }
        // System.out.println("0 " + isPrime(0));
        // System.out.println("1 " + isPrime(1));
        // System.out.println("2 " + isPrime(2));
        // System.out.println("12 " + isPrime(12));
        // System.out.println("-1 " + isPrime(-1));
        }

    public static boolean isPrime(int number) { //methode true/false is boolean met input Int
            if (number < 2) return false;
            for (int i = 2; i < number; i++) { //start with 2 (prime), first prime then stop 1 early because always true
                if (number % i == 0) return false;
            }
            return true;

    }
}
