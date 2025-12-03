package week8.src;

/**
 * The MyMath interface contains definitions for basic arithmetic operations
 * @author Connor Petri
 */
public interface MyMath {

    /**
     * Addition of 3 operands
     * @param x
     * @param y
     * @param z
     * @return Sum
     */
    public int add(int x, int y, int z);

    /**
     * Subtraction of 3 operands
     * @param starting_value
     * @param sub_x
     * @param sub_y
     * @return Difference
     */
    public int subtract(int starting_value, int sub_x, int sub_y);

    /**
     * Multiplication
     * @param x
     * @param y
     * @return Product
     */
    public int multiply(int x, int y);

    /**
     * Division
     * @param value_x
     * @param div_by_y
     * @return Quotient
     */
    public int divide(int value_x, int div_by_y);
}
