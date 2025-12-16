package week2;

import java.util.Scanner;

public class GatheringInput {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Input U.S. Dollar currency: $");
            float dollars = scanner.nextFloat();
            
            System.out.print("Input Exchange Rate: ");
            float exchangeRate = scanner.nextFloat();

            System.out.print("Target Currency: ");
            float finalAmount = Math.round((dollars * exchangeRate) * 100.0f) / 100.0f;
            System.out.println(String.format("%.2f", finalAmount));
        }
        
    }
}
