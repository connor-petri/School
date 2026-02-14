import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Holds information and methods for calendar events
 * @author Connor Petri
 * @see TimeInterval
 */
public class Event implements Comparable<Event> {
    protected String name;
    protected TimeInterval time;
    protected String recurrence;
    protected boolean isRecurrent;

    private static final String R_PATTERN = "MTWRFAS";

    protected final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    protected final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("E, MMM d yyyy");

    /**
     * Postcondition: Passes up IllegalArgumentException if invalid
     * @param s String
     * @throws IllegalArgumentException if s is invalid
     */
    private void checkPattern(String s) throws IllegalArgumentException {
        int p = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            while (p < R_PATTERN.length() && R_PATTERN.charAt(p) != c) {
                p++;
            }

            if (p == R_PATTERN.length()) {
                throw new IllegalArgumentException("Recurrence string must match MTWRFAS or some in-order combination");
            }

            p++;
        }
    }

    /**
     * Precondition: name must not be blank
     * Postcondition: constructs one-time event with blank recurrence string
     * @param name String
     * @param timeInterval TimeInterval
     * @throws IllegalArgumentException if name is blank
     */
    public Event(String name, TimeInterval timeInterval) throws IllegalArgumentException {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be blank");
        }

        this.name = name;
        this.time = timeInterval;
        this.recurrence = "";
        this.isRecurrent = false;
    }

    /**
     * Precondition: name must not be blank recurrence string must be valid
     * Postcondition: Event is constructed
     * @param name String
     * @param timeInterval TimeInterval
     * @param recurrence String
     * @throws IllegalArgumentException for blank names and invalid recurrence strings
     */
    public Event(String name, TimeInterval timeInterval, String recurrence) throws IllegalArgumentException {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be blank");
        }

        checkPattern(recurrence);

        this.name = name;
        this.time = timeInterval;
        this.recurrence = recurrence;
        this.isRecurrent = true;
    }

    /**
     * Postcondition: retrieves name
     * @return String name
     */
    public String getName() { return name; }

    /**
     * Postcondition: retrieves time
     * @return TimeInterval time
     */
    public TimeInterval getTime() { return time; }

    /**
     * Postcondition: retrieves recurrence string
     * @return String recurrence
     */
    public String getRecurrence() { return recurrence; }

    /**
     * Precondition: Name cannot be empty
     * Postcondition: Updates event name
     * @param name String
     * @throws IllegalArgumentException if name is empty
     */
    public void setName(String name) throws IllegalArgumentException {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("New event name cannot be blank");
        }
        this.name = name;
    }

    /**
     * Precondition: recurrence string must be valid
     * Postcondition: recurrence string is updated
     * @param r recurrence string
     * @throws IllegalArgumentException if r is invalid
     */
    public void setRecurrence(String r) throws IllegalArgumentException {
        checkPattern(r);
        recurrence = r;
    }

    /**
     * Postcondition: Returns whether this event is recurrent
     * @return true/false if recurrent
     */
    public boolean isRecurrent() { return isRecurrent; }

    /**
     * Postcondition: Determines if this event overlaps with the other event
     * @param other Event
     * @return true/false if there is an overlap
     */
    public boolean overlapsWith(Event other) {
        return time.overlapsWith(other.time);
    }

    /**
     * Postcondition: Prints event information followed by a new line
     */
    public void print() {
        System.out.println(name + ":");
        if (isRecurrent()) {
            System.out.print(recurrence + " " + timeFormatter.format(time.getStartTime()) + " - "
                            + timeFormatter.format(time.getEndTime()) + " " + dateFormatter.format(time.getStartTime())
                            + " " + dateFormatter.format(time.getEndTime()));
        } else {
            System.out.print(timeFormatter.format(time.getStartTime()) + " - " + timeFormatter.format(time.getEndTime())
                            + " " + dateFormatter.format(time.getStartTime()));
            if (time.getStartTime().getDayOfYear() != time.getEndTime().getDayOfYear()) {
                System.out.print(" " + dateFormatter.format(time.getEndTime()));
            }
        }
        System.out.println();
    }

    /**
     * Postcondition: satisfies Comparable implementation
     * @returns int > 0 if bigger < 0 if smaller
     */
    public int compareTo(Event other) {
        if (!time.getStartTime().equals(other.time.getStartTime())) {
            return time.getStartTime().compareTo(other.time.getStartTime());
        }
        return name.compareTo(other.getName());
    }
}
