package week4;
import java.util.*;

public class Driver {
    static ArrayList<Book> arr = new ArrayList<Book>();

    public static void main(String[] args) {
        try (Scanner s = new Scanner(System.in)) {
            for (int i = 0; i < 3; i++) {
                System.out.print("Book " + (i+1) + " Title: ");
                String title = s.nextLine();
                System.out.print("Book " + (i+1) + " Author: ");
                String author = s.nextLine();

                arr.add(new Book(title, author));
            }
        }

        System.out.println("Your Library:");
        for (Book b : arr) {
            System.out.println(b.getTitle() + " - " + b.getAuthor());
        }
    }
}
