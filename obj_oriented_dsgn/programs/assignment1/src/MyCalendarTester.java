import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;
import java.time.*;

public class MyCalendarTester {
    private static MyCalendar cal = new MyCalendar();
    private static final Scanner s = new Scanner(System.in);

    private static boolean dig(char c) { return Character.isDigit(c); }

    private static char getUserInput(String prompt, char[] valid) {
        System.out.println(prompt);
        char input = Character.toLowerCase(s.next().charAt(1));

        Arrays.sort(valid);
        while (Arrays.binarySearch(valid, input) < 0) {
            System.out.print("Invalid input, must be ");
            for (int i = 0; i < Array.getLength(valid); i++) {
                System.out.print(valid[i] + " ");
            }
            System.out.println(prompt);
            input = Character.toLowerCase(s.next().charAt(1));
        }
        return input;
    }

    private static void viewMenu() {
        char[] valid = { 'd', 'm' };
        char input = getUserInput("[D]ay view or [M]onth view?", valid);

        switch (input) {
            case 'd':
                dayView();
                break;
            case 'm':
                monthView();
        }
    }

    private static void printDayEvents(int year, int month, int day) {
        for (Event e : cal.getEvents()) {
            LocalDateTime t = e.getTime().getStartTime();
            if (year == t.getYear() && month == t.getMonthValue() && day == t.getDayOfMonth()) {
                e.print();
            }
        }
    }

    private static LocalDate getDate() {
        System.out.println("Enter a date [MM/DD/YYYY]");
        String d = s.next();
        int month = Integer.parseInt(d.substring(0,1));
        int day = Integer.parseInt(d.substring(3, 4));
        int year = Integer.parseInt(d.substring(6, 9));

        return LocalDate.of(year, month, day);
    }

    private static void dayView() {
        LocalDate date = getDate();

        printDayEvents(date.getYear(), date.getMonthValue(), date.getDayOfMonth());

        char[] valid = { 'p', 'n', 'g' };
        char input = getUserInput("[P]revious or [N]ext or [G]o back to the main menu?", valid);

        for (;;) {
            switch (input) {
                case 'p':
                    date = date.minusDays(1);
                    printDayEvents(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
                    break;
                case 'n':
                    date = date.plusDays(1);
                    printDayEvents(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
                    break;
                case 'g':
                    return;
            }
        }
    }

    private static void monthView() {
        return;
    }

    public static void create() {
        System.out.println("Enter Event Name");
        String name = s.nextLine();
        LocalDate date = getDate();

        System.out.println("Enter a start time (24hrs i.e. 06:00 or 13:30");
        String t = s.nextLine();
        LocalDateTime startTime = LocalDateTime.of(date.getYear(),
                date.getMonthValue(),
                date.getDayOfMonth(),
                Integer.parseInt(t.substring(0, 1)),
                Integer.parseInt(t.substring(3, 4)));

        System.out.println("Enter an end time (24hrs)");
        t = s.nextLine();
        LocalDateTime endTime = LocalDateTime.of(date.getYear(),
                date.getMonthValue(),
                date.getDayOfMonth(),
                Integer.parseInt(t.substring(0, 1)),
                Integer.parseInt(t.substring(3, 4)));

        cal.
    }


    private static void mainMenu() {
        char[] valid = { 'c', 'd', 'e', 'g', 'q', 'v' };

        for (;;) {
            char input = getUserInput("Please enter one of the following inputs:\n[V]iew by  [C]reate, [G]o to [E]vent list [D]elete  [Q]uit", valid);

            switch (input) {
                case 'v':
                    viewMenu();
                    break;
                case 'c':
                    create();
                    break;
                case 'g':
                    goTo();
                    break;
                case 'e':
                    eventList();
                    break;
                case 'd':
                    deleteEvent();
                    break;
                case 'q':
                    return;
            }
        }
    }

    public static void main(String[] args) {
        mainMenu();
    }
}