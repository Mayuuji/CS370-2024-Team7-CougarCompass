import java.util.List;

public class Route {
    private List<Coordinates> path;
    private double distance;
    private TimeSpan estimatedTime;

    // Getters and setters

    public List<Coordinates> getPath() {
        return path;
    }

    public void setPath(List<Coordinates> path) {
        this.path = path;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public TimeSpan getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(TimeSpan estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
}

