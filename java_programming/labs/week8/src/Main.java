package java_programming.labs.week8.src;

/**
 * Driver class to demonstrate use of MyMath interface and MathCore class.
 * @see MyMath
 * @see MathCore
 * @author Connor Petri
 */
public class Main {
    public static void main(String[] args) {
        int x = 1;
        int y = 2;
        int z = 3;

        MathCore core = new MathCore();

        System.out.println(x + " + " + y + " + " + z + " = " + core.add(x, y, z));
        System.out.println(x + " - " + y + " - " + z + " = " + core.subtract(x, y, z));
        System.out.println(x + " * " + y + " = " + core.multiply(x, y));
        System.out.println(z + " / " + y + " = " + core.divide(z, y));
    }
}
