package java_programming.labs.week6;

public class Main {
    public static void main(String[] args) {
        Data d1 = new Data(1);
        Data d2 = new Data(2);
        Data d3 = new Data(3);
        Data d4 = new Data(3);

        System.out.println("\nAfter .equals()");
        System.out.println("d1.equals(d2): " + d1.equals(d2));
        System.out.println("d3.equals(d4): " + d3.equals(d4));
    }
}
