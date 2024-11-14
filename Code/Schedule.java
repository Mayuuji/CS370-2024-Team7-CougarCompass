import java.util.List;

public class Schedule {
    private List<Course> courses;

    public Schedule(List<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    public Course getUpcomingClass(DateTime currentTime) {
        // Logic to find the next upcoming course based on current time
        return courses.stream()
                .filter(course -> course.getStartTime().isAfter(currentTime))
                .findFirst()
                .orElse(null);
    }

    // Additional getters and setters
}
