package java_programming.labs.classcomparison;

public class Data {
    private int x;

    Data(int x) { this.x = x; }
    
    public int get() { return x; }
    public void set(int x) { this.x = x; }

    public boolean equals(Data other) {
        return this.x == other.get();
    }
}
