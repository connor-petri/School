import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;
import java.time.*;
import java.time.format.*;

public class MyCalendarTester {
    private static MyCalendar cal = new MyCalendar();
    private static final Scanner s = new Scanner(System.in);

    private static boolean dig(char c) { return Character.isDigit(c); }

    private static char getUserInput(String prompt, char[] valid) {
        System.out.println(prompt);
        char input = Character.toLowerCase(s.next().charAt(0));
        s.nextLine();

        Arrays.sort(valid);
        while (Arrays.binarySearch(valid, input) < 0) {
            System.out.print("Invalid input, must be ");
            for (int i = 0; i < Array.getLength(valid); i++) {
                System.out.print(valid[i] + " ");
            }
            System.out.println(prompt);
            input = Character.toLowerCase(s.next().charAt(0));
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

    private static LocalDate getDate() {
        System.out.println("Enter a date [MM/DD/YYYY]");
        String d = s.nextLine();
        try {
            return LocalDate.parse(d, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use MM/DD/YYYY.");
            return getDate(); // recursive call to get the date again
        }
    }

    private static void dayView() {
        LocalDate date = LocalDate.now();

        cal.printDayView(date.getYear(), date.getMonthValue(), date.getDayOfMonth());

        char[] valid = { 'p', 'n', 'g' };

        for (;;) {
            char input = getUserInput("[P]revious or [N]ext or [G]o back to the main menu?", valid);
            switch (input) {
                case 'p':
                    date = date.minusDays(1);
                    cal.printDayView(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
                    break;
                case 'n':
                    date = date.plusDays(1);
                    cal.printDayView(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
                    break;
                case 'g':
                    return;
            }
        }
    }

    private static void monthView() {
        YearMonth ym = YearMonth.now();
        cal.printMonthView(ym);
        char[] valid = { 'p', 'n', 'g' };
        for (;;) {
            char input = getUserInput("[P]revious or [N]ext or [G]o back to the main menu?", valid);
            switch (input) {
                case 'p':
                    ym = ym.minusMonths(1);
                    cal.printMonthView(ym);
                    break;
                case 'n':
                    ym = ym.plusMonths(1);
                    cal.printMonthView(ym);
                    break;
                case 'g':
                    return;
            }
        }
    }

    public static void create() throws FileNotFoundException, IOException {
        System.out.println("Enter Event Name");
        String name = s.nextLine();
        LocalDate date = getDate();

        System.out.println("Enter a start time (24hrs i.e. 06:00 or 13:30)");
        String t = s.nextLine();
        LocalTime startTime = parseTime(t);
        System.out.println(startTime);

        System.out.println("Enter an end time (24hrs)");
        t = s.nextLine();
        LocalTime endTime = parseTime(t);
        System.out.println(endTime);

        LocalDateTime startDateTime = date.atTime(startTime);
        LocalDateTime endDateTime = date.atTime(endTime);

        cal.addEvent(new Event(name, new TimeInterval(startDateTime, endDateTime)));
    }

    private static LocalTime parseTime(String t) {
        try {
            return LocalTime.parse(t, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            System.out.println("Invalid time format. Please use HH:mm.");
            return parseTime(s.nextLine());
        }
    }

    private static void goTo() {
        LocalDate date = getDate();
        cal.printDayView(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }

    private static void deleteEvent() throws FileNotFoundException, IOException {
        char[] valid = { 's', 'a', 'r' };
        char input = getUserInput("[S]elected  [A]ll   [R]ecurring", valid);

        LocalDate date;
        switch (input) {
            case 's':
                date = getDate();
                cal.printOneTimeEvents(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
                System.out.println("Enter the name of the event you want to delete:");
                String name = s.nextLine();

                for (Event e : cal.getDayEvents(date)) {
                    if (e.getName().equals(name) && !e.isRecurrent()) {
                        cal.deleteEvent(e);
                    }
                }
                break;
            case 'a':
                date = getDate();
                for (Event e : cal.getDayEvents(date)) {
                    cal.deleteEvent(e);
                }
                break;
            case 'r':
                System.out.println("Enter name of recurring event:");
                String n = s.nextLine();
                for (Event e : cal.getRecurringEvents()) {
                    if (e.getName().equals(n)) {
                        cal.deleteEvent(e);
                    }
                }
                break;
        }
    }

    private static void mainMenu() throws IOException, FileNotFoundException {
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
                    cal.printEvents();
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
        try {
            cal.load();
            mainMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}