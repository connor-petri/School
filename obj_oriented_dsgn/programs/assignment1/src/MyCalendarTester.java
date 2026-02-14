import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;
import java.time.*;
import java.time.format.*;

/**
 * Driver class for calendar app.
 * @author Connor Petri
 * @version 1.0
 * @see Event
 * @see TimeInterval
 * @see MyCalendar
 */
public class MyCalendarTester {
    private static MyCalendar cal = new MyCalendar();
    private static final Scanner s = new Scanner(System.in);

    /**
     * Macro for Character.isDigit
     * @param c
     * @return
     */
    private static boolean dig(char c) { return Character.isDigit(c); }

    /**
     * Gathers input from the user in the form of a single character and validates it against a character array
     * Precondition: prompt and valid char array must not be null
     * Postcondition: User input is validated against input array
     * @param prompt What to prompt the user with
     * @param valid Character array of valid inputs
     * @return Validated user input
     */
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
        System.out.println();
        return input;
    }

    /**
     * Prompts the user to choose day or month view and calls the appropriate function
     * Postcondition: Correct view menu is called
     */
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

    /**
     * Prompts the user to enter a date in MM/DD/YYYY format and validates their input
     * Precondition: User must enter valid date
     * Postcondition: Valid LocalDate object is created
     * @return LocalDate object from user input
     */
    private static LocalDate getDate() {
        System.out.println("Enter a date [MM/DD/YYYY]");
        String d = s.nextLine();
        try {
            return LocalDate.parse(d, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use MM/DD/YYYY.");
            return getDate();
        }
    }

    /**
     * Shows all events in a given day separated by one-time and recurring events. Prompts user to move back or forward a day or return to menu
     * Postcondition: Prints day view
     */
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

    /**
     * Prints a calendar view to console with days with events having curly braces around them. Prompts the user to go back or forward a month or return to menu
     * Postcondition: Prints month view
     */
    private static void monthView() {
        YearMonth ym = YearMonth.now();
        cal.printMonthView(ym, true);
        char[] valid = { 'p', 'n', 'g' };
        for (;;) {
            char input = getUserInput("[P]revious or [N]ext or [G]o back to the main menu?", valid);
            switch (input) {
                case 'p':
                    ym = ym.minusMonths(1);
                    cal.printMonthView(ym, true);
                    break;
                case 'n':
                    ym = ym.plusMonths(1);
                    cal.printMonthView(ym, true);
                    break;
                case 'g':
                    return;
            }
        }
    }

    /**
     * Prompts the user to enter a name, date, start time, and end time for a new event, validates their input, and creates a new event on the calendar and writes it to events.txt
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void create() throws FileNotFoundException, IOException {
        System.out.println("Enter Event Name");
        String name = s.nextLine();
        LocalDate date = getDate();

        System.out.println("Enter a start time (24hrs i.e. 06:00 or 13:30)");
        String t = s.nextLine();
        LocalTime startTime = parseTime(t);

        System.out.println("Enter an end time (24hrs)");
        t = s.nextLine();
        LocalTime endTime = parseTime(t);

        LocalDateTime startDateTime = date.atTime(startTime);
        LocalDateTime endDateTime = date.atTime(endTime);

        Event ev = new Event(name, new TimeInterval(startDateTime, endDateTime));

        for (Event e : cal.events) {
            if (e.overlapsWith(ev)) {
                System.out.println("Event overlaps with " + e.getName());
                return;
            }
        }

        cal.addEvent(ev);
    }

    /**
     * Turns a time string in HH:mm format, validates it, and makes it into a LocalTime object
     * Precondition: t must not be null and be in HH:mm format
     * @param t time string
     * @return LocalTime object based on validated string t
     */
    private static LocalTime parseTime(String t) {
        try {
            return LocalTime.parse(t, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            System.out.println("Invalid time format. Please use HH:mm.");
            return parseTime(s.nextLine());
        }
    }

    /**
     * Prints the day view of a specified date
     * Postcondition: Prints day view from gathered input
     */
    private static void goTo() {
        LocalDate date = getDate();
        cal.printDayView(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }

    /**
     * Prompts the user to delete a selected event, all events on a given day, or a recurring event
     * Postcondition: event is deleted from calendar and from events.txt
     * @throws FileNotFoundException
     * @throws IOException
     */
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
                        return;
                    }
                }
                System.out.println("Event with name " + name + " not found");
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
                        return;
                    }
                }
                System.out.println("Event with name " + n + " not found");
                break;
        }
    }

    /**
     * Displays the main menu and gathers/validates user input. Calls appropriate function based on user input
     * Precondition: Input must be valid
     * Postcondition: Proper calendar function is called
     * @throws IOException
     * @throws FileNotFoundException
     */
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

    /**
     * Main method. Loads events from events.txt, Prints the starting calendar, and calls the main menu function
     * @param args Command line args
     */
    public static void main(String[] args) {
        try {
            cal.load();
        } catch (Exception e) {
            System.out.println("events.txt not found. Please check location.");
            return;
        }
        cal.printMonthView(YearMonth.now(), false);
        try {
            mainMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}