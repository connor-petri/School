package java_programming.labs.week5;

import java.io.IOException;

public class PlanetCount {
    private static int[] counts = new int[7];

    static void countWords(String input) {
        
        String[] words = input.split(" ");

        for (String word : words) {
            switch (word.toLowerCase()) {
                case "sun":
                    counts[0]++;
                    break;
                case "moon":
                    counts[1]++;
                    break;
                case "saturn":
                    counts[2]++;
                    break;
                case "mars":
                    counts[3]++;
                    break;
                case "venus":
                    counts[4]++;
                    break;
                case "earth":
                    counts[5]++;
                    break;
                default:
                    counts[6]++;
                    break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String inputString = "All hail Mars the god of war.";
        
        countWords(inputString);

        System.out.printf("""
                Name   | Count
                Sun    | %d
                Moon   | %d
                Saturn | %d
                Mars   | %d
                Venus  | %d
                Earth  | %d
                Other  | %d
                """, counts[0], counts[1], counts[2], counts[3], counts[4], counts[5], counts[6]);
    }

}
