public class Course {
    private String courseID;
    private String courseName;
    private Location location;
    private DateTime startTime;
    private DateTime endTime;

    public Course(String courseID, String courseName, Location location, DateTime startTime, DateTime endTime) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }
}
