import java.lang.reflect.Array;
import java.util.TreeSet;
import java.time.*;
import java.time.format.*;
import java.io.*;

public class MyCalendar {
    protected TreeSet<Event> events = new TreeSet<>();
    protected static final DateTimeFormatter dateForm = DateTimeFormatter.ofPattern("E, MMM d, yyyy");
    protected static final DateTimeFormatter timeForm = DateTimeFormatter.ofPattern("HH:mm");
    protected static final DateTimeFormatter saveDateForm = DateTimeFormatter.ofPattern("MM/dd/yyyy");

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

    private char convertDayToChar(DayOfWeek day) {
        return switch (day) {
            case MONDAY -> 'M';
            case TUESDAY -> 'T';
            case WEDNESDAY -> 'W';
            case THURSDAY -> 'R';
            case FRIDAY -> 'F';
            case SATURDAY -> 'A';
            case SUNDAY -> 'S';
            default -> throw new IllegalArgumentException("Invalid day");
        };
    }

    public void printDayView(int year, int month, int day) {
        LocalDate givenDate = LocalDate.of(year, month, day);
        System.out.println(dateForm.format(LocalDate.of(year, month, day)));

        for (Event e : events) {
            LocalDateTime startDateTime = e.getTime().getStartTime();
            LocalDateTime endDateTime = e.getTime().getEndTime();

            if (!e.isRecurrent()) {
                if (startDateTime.toLocalDate().equals(givenDate)) {
                    e.print();
                }
            } else {
                LocalDate startDate = startDateTime.toLocalDate();
                LocalDate endDate = endDateTime.toLocalDate();

                if ((givenDate.isEqual(startDate) || givenDate.isAfter(startDate)) &&
                        (givenDate.isEqual(endDate) || givenDate.isBefore(endDate))) {

                    String recurrence = e.getRecurrence();

                    char dayChar = convertDayToChar(givenDate.getDayOfWeek());

                    if (recurrence.indexOf(dayChar) != -1) {
                        e.print();
                    }
                }
            }
        }
    }

    public void printOneTimeEvents(int year, int month, int day) {
        System.out.println(dateForm.format(LocalDate.of(year, month, day)));
        for (Event e : events) {
            LocalDateTime t = e.getTime().getStartTime();
            if (year == t.getYear() && month == t.getMonthValue() && day == t.getDayOfMonth() && !e.isRecurrent()) {
                e.print();
            }
        }
    }

    public void printMonthView(YearMonth yearMonth) {
        System.out.println(yearMonth.getMonth() + " " + yearMonth.getYear());
        System.out.println("Su Mo Tu We Th Fr Sa");
        int slot = 0;

        int firstDayIndex = yearMonth.atDay(1).getDayOfWeek().getValue() % 7;

        for (int i = 0; i < firstDayIndex; i++) {
            System.out.print("   ");
            slot++;
        }

        for (int day = 1; day < yearMonth.lengthOfMonth(); day++) {
            if (slot % 7 == 0 && slot != 0) {
                System.out.println();
            }
            if (!getDayEvents(LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), day)).isEmpty()) {
                System.out.print("{");
            }
            if (day < 10) {
                System.out.print("0");
            }
            System.out.print(day);
            if (!getDayEvents(LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), day)).isEmpty()) {
                System.out.print("}");
            }
            System.out.print(" ");
            slot++;
        }

        System.out.println();
    }

    public void addEvent(Event e) throws FileNotFoundException, IOException {
        events.add(e);

        FileWriter fw = new FileWriter(System.getProperty("user.dir") + "/events.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(e.getName() + "\n");
        bw.write(timeForm.format(e.getTime().getStartTime()));
        bw.write(" ");
        bw.write(timeForm.format(e.getTime().getEndTime()));
        bw.write(" ");
        bw.write(saveDateForm.format(e.getTime().getStartTime()));
        bw.write(" ");
        bw.write(saveDateForm.format(e.getTime().getEndTime()) + "\n");

        bw.close();
        fw.close();
    }

    public void deleteEvent(Event e) throws FileNotFoundException, IOException {
        events.remove(e);
        FileReader fr = new FileReader(System.getProperty("user.dir") + "/events.txt");
        BufferedReader br = new BufferedReader(fr);
        String name, contents = "";

        while((name = br.readLine()) != null) {
            if (!e.getName().equals(name)) {
                contents += name + "\n" + br.readLine() + "\n";
            } else {
                br.readLine();
            }
        }
        br.close();
        fr.close();
        FileWriter fw = new FileWriter(System.getProperty("user.dir") + "/events.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(contents);

        bw.close();
        fw.close();
    }

    public void printEvents() {
        System.out.println("ONE-TIME EVENTS:\n");
        for (Event e : events) {
            if (!e.isRecurrent()) {
                e.print();
            }
        }

        System.out.println();

        System.out.println("RECURRING EVENTS:\n");
        for (Event e : events) {
            if (e.isRecurrent()) {
                e.print();
            }
        }
    }

    public TreeSet<Event> getDayEvents(LocalDate date) {
        TreeSet<Event> ret = new TreeSet<>();
        for (Event e : events) {
            if (!e.isRecurrent()) {
                LocalDate eventDate = e.getTime().getStartTime().toLocalDate();
                if (date.isEqual(eventDate)) {
                    ret.add(e);
                }
            } else {
                LocalDate startDate = e.getTime().getStartTime().toLocalDate();
                LocalDate endDate = e.getTime().getEndTime().toLocalDate();

                boolean withinRange =
                        (date.isEqual(startDate) || date.isAfter(startDate)) &&
                                (date.isEqual(endDate) || date.isBefore(endDate));

                if (withinRange) {
                    char dayChar = convertDayToChar(date.getDayOfWeek());

                    if (e.getRecurrence().indexOf(dayChar) != -1) {
                        ret.add(e);
                    }
                }
            }
        }
        return ret;
    }

    public void load() throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(System.getProperty("user.dir") + "/events.txt");
        BufferedReader br = new BufferedReader(fr);
        String name;
        String[] details;
        LocalDateTime start, end;

        while ((name = br.readLine()) != null) {
            details = br.readLine().trim().split("\\s+");

            LocalDate s = LocalDate.parse(details[2], DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            LocalDate e = LocalDate.parse(details[3], DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            LocalTime st = LocalTime.parse(details[0], DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime et = LocalTime.parse(details[1], DateTimeFormatter.ofPattern("HH:mm"));

            start = LocalDateTime.of(s.getYear(), s.getMonthValue(), s.getDayOfMonth(), st.getHour(), st.getMinute());
            end = LocalDateTime.of(e.getYear(), e.getMonthValue(), e.getDayOfMonth(), et.getHour(), et.getMinute());

            if (Array.getLength(details) == 5) {
                events.add(new Event(name, new TimeInterval(start, end), details[4]));
            } else {
                events.add(new Event(name, new TimeInterval(start, end)));
            }
        }

        br.close();
        fr.close();
    }
}