package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AvailabilitySlot {
    private DayOfWeek day;
    private LocalTime time;

    public AvailabilitySlot(DayOfWeek day, LocalTime time) {
        this.day = day;
        this.time = time;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public LocalTime getTime() {
        return time;
    }

    public LocalDateTime getNextOccurrence(LocalDate fromDate) {
        int today = fromDate.getDayOfWeek().getValue();
        int target = day.getValue();
        int daysToAdd = (target - today + 7) % 7;
        return fromDate.plusDays(daysToAdd).atTime(time);
    }

    public LocalDateTime getNextOccurrence() {
        return getNextOccurrence(LocalDate.now());
    }

    @Override
    public String toString() {
        return day + " at " + time;
    }
}