import java.util.List;

public class Student {
    private String studentID;
    private String name;
    private Coordinates currentLocation;
    private Schedule schedule;
    private Preferences preferences;

    public Student(String studentID, String name, Coordinates currentLocation, Schedule schedule, Preferences preferences) {
        this.studentID = studentID;
        this.name = name;
        this.currentLocation = currentLocation;
        this.schedule = schedule;
        this.preferences = preferences;
    }

    public void updateLocation(Coordinates newLocation) {
        this.currentLocation = newLocation;
    }

    public Location getDestination() {
        Course nextCourse = schedule.getUpcomingClass(/* current time */);
        return nextCourse != null ? nextCourse.getLocation() : null;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    // Additional getters and setters if needed
}

