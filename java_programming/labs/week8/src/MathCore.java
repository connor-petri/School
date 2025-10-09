package java_programming.labs.week8.src;

/**
 * An implementation of the MyMath Interface
 * @see MyMath
 * @author Connor Petri
 */

public class MathCore implements MyMath {
    /**
     * Returns the Sum of the 3 input parameters.
     * @param x
     * @param y
     * @param z
     * @return x + y + z
     */
    public int add(int x, int y, int z) {
        return x + y + z;
    }

    /**
     * Calculate the difference between starting_value and sub_y
     * @param starting_value
     * @param sub_x
     * @param sub_y
     * @return Difference between starting_value and sub_y
     */
    public int subtract(int starting_value, int sub_x, int sub_y) {
        return starting_value - sub_x - sub_y;
    }

    /**
     * Multiply 2 integers
     * @param x
     * @param y
     * @return The product of x and y.
     */
    public int multiply(int x, int y) {
        return x * y;
    }

    /**
     * Divide one integer by another
     * @param value_x
     * @param div_by_y
     * @return value_x / div_by_y
     */
    public int divide(int value_x, int div_by_y) {
        return value_x / div_by_y;
    }
}
