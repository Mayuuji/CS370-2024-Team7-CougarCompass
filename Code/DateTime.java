import java.time.LocalDateTime;

public class DateTime {
    private LocalDateTime dateTime;

    public DateTime(int year, int month, int day, int hour, int minute) {
        this.dateTime = LocalDateTime.of(year, month, day, hour, minute);
    }

    public boolean isAfter(DateTime other) {
        return this.dateTime.isAfter(other.dateTime);
    }

    public boolean isBefore(DateTime other) {
        return this.dateTime.isBefore(other.dateTime);
    }

    public LocalDateTime getLocalDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return dateTime.toString();
    }
}

