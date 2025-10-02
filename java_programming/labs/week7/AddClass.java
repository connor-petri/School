package java_programming.labs.week7;

public class AddClass implements IAdd {
    private int x, y;

    public int add_xy(int x, int y) { 
        this.x = x;
        this.y = y;

        System.out.println("x: " + x + " y: " + y);
        return x + y; 
    }

    public int get_x() { return x; }
    public int get_y() { return y; }

    public void change_x(int x) { this.x = x; }
    public void change_y(int y) { this.y = y; }
}
