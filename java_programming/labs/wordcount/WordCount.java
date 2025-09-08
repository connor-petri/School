package java_programming.labs.wordcount;

import java.util.Scanner;

public class WordCount {
    public static void main(String[] args) {
        int numSea = 0;
        int numStarfish = 0;
        int numDolphins = 0;

        try (Scanner s = new Scanner(System.in)) {
            System.out.print("Enter a phrase: ");
            String[] phrase = s.nextLine().split(" ");

            for (String word : phrase) {
                if (word.equalsIgnoreCase("sea")) {
                    numSea++;
                } else if (word.equalsIgnoreCase("starfish")) {
                    numStarfish++;
                } else if (word.equalsIgnoreCase("dolphins")) {
                    numDolphins++;
                }
            }
        }

        System.out.println("Word repition count:");
        System.out.println("\"sea\": " + numSea);
        System.out.println("\"starfish\": " + numStarfish);
        System.out.println("\"dolphin\": " + numDolphins);
    }
}
