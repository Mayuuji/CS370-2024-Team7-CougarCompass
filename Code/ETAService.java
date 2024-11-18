public class ETAService {

    public double avgWalkingSpeed = 2.6; // mph
    public double avgSpeedDisabled = 1.5; // mph
    public TimeSpan calculateETA(Route route) {
        // Calculation logic for ETA based on route distance and speed assumptions
        return route.getEstimatedTime();
    }
}
