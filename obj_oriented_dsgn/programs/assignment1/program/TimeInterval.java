import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * TimeInterval class stores a start and end time and methods to support their use
 *
 * @author Connor Petri
 * @see java.time.LocalDateTime
 */
public class TimeInterval implements Comparable<TimeInterval> {
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;

    /**
     * Constructs TimeInterval from a start and finish LocalDateTime
     * Precondition: start must be before end
     * Postcondition: TimeInterval is constructed with the given parameters
     * @param start LocalDateTime
     * @param end LocalDateTime
     * @throws IllegalArgumentException if end is before start
     */
    public TimeInterval(LocalDateTime start, LocalDateTime end) throws IllegalArgumentException {
        if (end.compareTo(start) < 0) {
            throw new IllegalArgumentException("End time must be after start time");
        }
        startTime = start;
        endTime = end;
    }

    /**
     * PostCondition: Retrieves the start time of the event
     * @return the start LocalDateTime of the event
     */
    public LocalDateTime getStartTime() { return startTime; }

    /**
     * PostCondition: Retrieves the end time of the event
     * @return the end LocalDateTime of the event
     */
    public LocalDateTime getEndTime() { return endTime; }

    /**
     * Precondition: time must be before endTime
     * Postcondition: startTime is updated
     * @param time LocalDateTime
     * @throws IllegalArgumentException
     */
    public void setStartTime(LocalDateTime time) throws IllegalArgumentException {
        if (endTime.compareTo(time) < 0) {
            throw new IllegalArgumentException("New start time must be before end time");
        }
        startTime = time;
    }

    /**
     * Precondition: time must be after startTime
     * Postcondition: endTime is updated
     * @param time LocalDateTime
     * @throws IllegalArgumentException
     */
    public void setEndTime(LocalDateTime time) throws IllegalArgumentException {
        if (startTime.compareTo(time) > 0) {
            throw new IllegalArgumentException("New end time must be after startTime");
        }
        endTime = time;
    }

    /**
     * Postcondition: true is returned if the two intervals overlap else false is returned
     * @param other TimeInterval
     * @return boolean
     */
    public boolean overlapsWith(TimeInterval other) {
        return (startTime.compareTo(other.startTime) < 0 && other.startTime.compareTo(endTime) < 0) ||
                (other.startTime.compareTo(startTime) < 0 && startTime.compareTo(other.endTime) < 0);
    }

    /**
     * Postcondition: Returns comparison integer based off of both event's start times
     * @param other TimeInterval
     * @return integer < 0 if this is smaller than other > 0 if larger than other 0 if equal
     */
    public int compareTo(TimeInterval other) {
        return startTime.compareTo(other.startTime);
    }
}