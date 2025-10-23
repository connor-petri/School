package java_programming.labs.week10;

import java.util.*;
import java.io.*;

public class Main {
    static ArrayList<String> lines = new ArrayList<String>();

    static void readMath() throws IOException {
        File f = new File("/home/cpetri/School/java_programming/labs/week10/math.txt");
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);

        String line;
        boolean done = false;

        while (!done) {
            line = br.readLine();
            if (line == null) 
                done = true;
            else
                lines.add(line);
        }

        br.close();
        fr.close();
    }

    static void processMath() throws IOException {
        File f = new File("/home/cpetri/School/java_programming/labs/week10/Results.txt");
        FileWriter fw = new FileWriter(f);
        PrintWriter pw = new PrintWriter(fw);

        String[] split;
        int result = 0;
        boolean unknown = false;
        

        for (String line : lines) {
            
            if (line.contains("+")) {
                split = line.split("\\+");
                result = Integer.parseInt(split[0].trim()) + Integer.parseInt(split[1].trim());
            } else if (line.contains("-")) {
                split = line.split("-");
                result = Integer.parseInt(split[0].trim()) - Integer.parseInt(split[1].trim());
            } else if (line.contains("x")) {
                split = line.split("x");
                result = Integer.parseInt(split[0].trim()) * Integer.parseInt(split[1].trim());
            } else if (line.contains("/")) {
                split = line.split("/");
                result = Integer.parseInt(split[0].trim()) / Integer.parseInt(split[1].trim());
            } else {
                unknown = true;
            }
            
            if (unknown)
                pw.println(line + " = unknown");
            else
                pw.println(line + " = " + result);


            unknown = false;
        }

        pw.close();
        fw.close();
    }

    public static void main(String[] args) {
        try {
           readMath();
           processMath();
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
