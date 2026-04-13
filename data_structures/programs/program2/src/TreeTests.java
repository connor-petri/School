import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Random;
import java.util.TreeSet;

public class TreeTests
{
    private Tree randTree(int n) {
        Tree t = new Tree();
        TreeSet<Integer> generated = new  TreeSet<>();
        int num;
        boolean done = false;
        Random r = new Random();

        for (int i = 0; i < n; i++) {
            done = false;
            do {
                num = r.nextInt( n + 1000);
                t.insert(num);
                if (!generated.contains(num)) {
                    done = true;
                    generated.add(num);
                }
            } while(!done);
        }

        return t;
    }

    @Test
    public void singleNodeTree()
    {
        Tree t = new Tree();
        t.insert(9);

        assertEquals(1, t.size(9));
        assertEquals(0, t.size(8));
        assertEquals(0, t.size(10));
        assertEquals(9, t.get(0));

        t.insert(15);
        assertEquals(2, t.size(9));
        assertEquals(0, t.size(8));
        assertEquals(0, t.size(10));
        assertEquals(2, t.size(15));
        assertEquals(0, t.size(18));

        assertEquals(15, t.get(1));

        t = new Tree();
        t.insert(15);
        t.insert(9);
        assertEquals(2, t.size(9));
        assertEquals(0, t.size(8));
        assertEquals(0, t.size(10));
        assertEquals(2, t.size(15));
        assertEquals(0, t.size(18));

        assertEquals(9, t.get(0));
        assertEquals(15, t.get(1));
    }

    @Test
    public void oneSplitLeft()
    {
        Tree t = new Tree();

        t.insert(9);
        t.insert(15);
        t.insert(1);

        assertEquals(3, t.size(9));
        assertEquals(1, t.size(15));
        assertEquals(0, t.size(17));
        assertEquals(0, t.size(11));

        assertEquals(1, t.size(1));
        assertEquals(0, t.size(0));
        assertEquals(0, t.size(3));

        assertEquals(1, t.get(0));
        assertEquals(9, t.get(1));
        assertEquals(15, t.get(2));

        assertEquals(3,t.size());
    }

    @Test
    public void oneSplitRight()
    {
        Tree t = new Tree();
        t.insert(1);
        t.insert(9);
        t.insert(15);

        assertEquals(3, t.size(9));
        assertEquals(1, t.size(15));
        assertEquals(0, t.size(17));
        assertEquals(0, t.size(11));

        assertEquals(1, t.size(1));
        assertEquals(0, t.size(0));
        assertEquals(0, t.size(3));

        assertEquals(1, t.get(0));
        assertEquals(9, t.get(1));
        assertEquals(15, t.get(2));
        assertEquals(3,t.size());


    }

    @Test
    public void oneSplitMiddle()
    {
        Tree t = new Tree();
        t.insert(1);
        t.insert(15);
        t.insert(9);

        assertEquals(3, t.size(9));
        assertEquals(1, t.size(15));
        assertEquals(0, t.size(17));
        assertEquals(0, t.size(11));

        assertEquals(1, t.size(1));
        assertEquals(0, t.size(0));
        assertEquals(0, t.size(3));

        assertEquals(1, t.get(0));
        assertEquals(9, t.get(1));
        assertEquals(15, t.get(2));
        assertEquals(3,t.size());
    }

    @Test
    public void twoSplitLeft()
    {
        Tree t = new Tree();
        t.insert(9);
        t.insert(15);
        t.insert(2);
        t.insert(4);
        t.insert(6);

        assertEquals(5, t.size(9));
        assertEquals(5, t.size(4));
        assertEquals(0, t.size(17));
        assertEquals(1, t.size(2));
        assertEquals(1, t.size(6));
        assertEquals(1, t.size(15));

        assertEquals(2, t.get(0));
        assertEquals(4, t.get(1));
        assertEquals(6, t.get(2));
        assertEquals(9, t.get(3));
        assertEquals(15, t.get(4));

        t.insert(1);
        t.insert(3);

        assertEquals(7, t.size(4));
        assertEquals(3, t.size(2));
        assertEquals(3, t.size(9));
        assertEquals(1, t.size(1));
        assertEquals(1, t.size(3));
        assertEquals(1, t.size(6));
        assertEquals(1, t.size(15));

        assertEquals(1, t.get(0));
        assertEquals(2, t.get(1));
        assertEquals(3, t.get(2));
        assertEquals(4, t.get(3));
        assertEquals(6, t.get(4));
        assertEquals(9, t.get(5));
        assertEquals(15, t.get(6));
    }

    @Test
    public void twoSplitMid() {
        Tree t = new Tree();
        t.insert(1);
        t.insert(15);
        t.insert(9);
        t.insert(2);
        t.insert(3);

        assertEquals(1, t.get(0));
        assertEquals(2, t.get(1));
        assertEquals(3, t.get(2));
        assertEquals(9, t.get(3));
        assertEquals(15, t.get(4));

        t.insert(4);
        t.insert(5);

        assertEquals(1, t.get(0));
        assertEquals(2, t.get(1));
        assertEquals(3, t.get(2));
        assertEquals(4, t.get(3));
        assertEquals(5, t.get(4));
        assertEquals(9, t.get(5));
        assertEquals(15, t.get(6));
    }

    @Test
    public void twoSplitRight() {
        Tree t = new Tree();
        t.insert(1);
        t.insert(9);
        t.insert(15);
        t.insert(16);
        t.insert(17);

        assertEquals(1, t.get(0));
        assertEquals(9, t.get(1));
        assertEquals(15, t.get(2));
        assertEquals(16, t.get(3));
        assertEquals(17, t.get(4));

        t.insert(18);
        t.insert(19);

        assertEquals(1, t.get(0));
        assertEquals(9, t.get(1));
        assertEquals(15, t.get(2));
        assertEquals(16, t.get(3));
        assertEquals(17, t.get(4));
        assertEquals(18, t.get(5));
        assertEquals(19, t.get(6));
    }

    @Test
    public void addToLeft() {
        int n = 10000000;
        Tree t = new Tree();
        t.insert(n + 10);
        t.insert(n + 11);
        t.insert(n + 12);

        for (int i = n - 1; i >= 0; i--) {
            t.insert(i);
        }

        for (int i = 0; i < n; i++) {
            assertEquals(i, t.get(i));
        }
        assertEquals(n + 10, t.get(n));
        assertEquals(n + 11, t.get(n + 1));
        assertEquals(n + 12, t.get(n + 2));
    }

    @Test
    public void addToMiddle() {
        int n = 1000;
        Tree t = new Tree();

        t.insert(-999);
        t.insert(n + 10);
        t.insert(-10);
        t.insert(n + 100);
        t.insert(n + 50);

        for (int i = 0; i < n; i++) {
            t.insert(i);
        }

        assertEquals(-999, t.get(0));
        assertEquals(-10, t.get(1));
        for (int i = 0; i < n; i++) {
            assertEquals(i, t.get(i+2));
        }

        assertEquals(n + 50, t.get(t.size() - 2));
        assertEquals(n + 100, t.get(t.size() - 1));
    }

    @Test
    public void addToRight() {
        int n = 1000;
        Tree t = new Tree();

        t.insert(-999);
        t.insert(-998);
        t.insert(-997);

        for  (int i = 0; i < n; i++) {
            t.insert(i);
        }

        assertEquals(-999, t.get(0));
        assertEquals(-998, t.get(1));
        assertEquals(-997, t.get(2));

        for (int i = 0; i < n; i++) {
            assertEquals(i, t.get(i + 3));
        }
    }

    @Test
    public void testDuplicates()
    {
        Tree t = new Tree();
        t.insert(1);
        t.insert(1);
        t.insert(9);
        t.insert(15);
        t.insert(13);
        t.insert(20);
        t.insert(7);
        t.insert(4);
        t.insert(1);
        t.insert(9);
        t.insert(15);
        t.insert(1);
        t.insert(9);
        t.insert(15);
        t.insert(13);
        t.insert(20);
        t.insert(7);
        t.insert(4);
        t.insert(13);
        t.insert(20);
        t.insert(7);
        t.insert(4);

        assertEquals(7, t.size(9));
        assertEquals(3, t.size(4));
        assertEquals(3, t.size(15));

        assertEquals(0, t.size(12));
        assertEquals(1, t.size(13));
        assertEquals(0, t.size(14));
        assertEquals(0, t.size(19));
        assertEquals(1, t.size(20));
        assertEquals(0, t.size(21));

        assertEquals(1, t.size(1));
        assertEquals(0, t.size(0));
        assertEquals(0, t.size(3));

        assertEquals(0, t.size(6));
        assertEquals(1, t.size(7));
        assertEquals(0, t.size(8));

        assertEquals(1, t.get(0));
        assertEquals(4, t.get(1));
        assertEquals(7, t.get(2));
        assertEquals(9, t.get(3));
        assertEquals(13, t.get(4));
        assertEquals(15, t.get(5));
        assertEquals(20, t.get(6));
        assertEquals(7,t.size());
        // Commented out because webcat doesn't know what assertThrows is. This works on my machine
//        assertThrows(ArrayIndexOutOfBoundsException.class, () -> t.get(t.size()));
    }

    @Test
    public void massiveTree() {
        int n = 10000;
        Random r = new Random();
        Tree t = randTree(n);
        assertEquals(n, t.size());

        t.insert(-1);
        assertEquals(-1, t.get(0));
        t.insert(n + 10000);
        assertEquals(n + 10000, t.get(t.size()-1));
    }

    @Test
    public void emptyTreeTest() {
        Tree t = new Tree();
        assertEquals(0, t.size());
        assertEquals(0, t.size(5));
        // Commented out bc webcat doesn't know what assertThrows is
//        assertThrows(ArrayIndexOutOfBoundsException.class, () -> t.get(5));
    }
}