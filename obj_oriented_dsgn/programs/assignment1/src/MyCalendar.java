import com.sun.source.tree.Tree;

import java.util.TreeSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class MyCalendar {
    protected TreeSet<Event> events = new TreeSet<>();
    protected static final DateTimeFormatter dateForm = DateTimeFormatter.ofPattern("E, MMM d, yyyy");
    protected static final DateTimeFormatter timeForm = DateTimeFormatter.ofPattern("E, MMM d, yyyy");

    public TreeSet<Event> getEvents() { return events; }
    public TreeSet<Event> getOneTimeEvents() {
        TreeSet<Event> ret = new TreeSet<>();
        for (Event e : events) {
            if (!e.isRecurrent()) {
                ret.add(e);
            }
        }
        return ret;
    }
    public TreeSet<Event> getRecurringEvents() {
        TreeSet<Event> ret = new TreeSet<>();
        for (Event e : events) {
            if (e.isRecurrent()) {
                ret.add(e);
            }
        }
        return ret;
    }

    public void printDayView(LocalDate date) {
        System.out.println(dateForm.format(date));
        for (Event e : events) {
            if (e.getTime().startTime.getDayOfYear() == date.getDayOfYear()) {
                System.out.println(e.getName() + ": " + timeForm.format(e.getTime().getStartTime())
                                    + " - " + timeForm.format(e.getTime().getStartTime()));
            }
        }
    }

    public void printMonthView(YearMonth yearMonth) {
        System.out.println(yearMonth.getMonth() + " " + yearMonth.getYear());
        System.out.println("Su Mo Tu We Th Fr Sa");

        int firstDayIndex = yearMonth.atDay(1).getDayOfWeek().getValue() % 7;

        for (int i = 0; i < firstDayIndex; i++) {
            System.out.print("   ");
        }

        for (int day = 1; day < yearMonth.lengthOfMonth(); day++) {
            
        }
    }
}