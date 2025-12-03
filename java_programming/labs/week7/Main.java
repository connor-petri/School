package week7;

public class Main {
    public static void main(String[] args) {
        AddClass a = new AddClass();
        IAdd i = a;

        System.out.println("Sum: " + i.add_xy(3, 4));

        a.change_x(5);
        a.change_y(6);

        System.out.println("Sum: " + a.add_xy(7, 8));
    }
}

// The add_xy() function always modifies AddClass's x and y values, so any
// change made by the other mutator functions is overwritten, and thus
// change_x and change_y are redundant.
