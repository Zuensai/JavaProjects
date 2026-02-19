import java.util.Scanner;

public class Calculator {
    public static void main(String [] args) {
        var reader = new Scanner(System.in);
        System.out.println("First number: ");
        double num1 = reader.nextDouble();
        System.out.println("Second number: ");
        double num2 = reader.nextDouble();
        double som = num1 + num2;
        System.out.println("The result from " + num1 + " + " + num2 + " = " + som);
        reader.close();

    }
}
/* Exercise 1
1. get first value
2. get second value
3. add them together
4. display the result 

extra:" Closen scanner properly */

