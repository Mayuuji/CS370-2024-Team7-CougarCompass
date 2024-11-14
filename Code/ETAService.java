public class ETAService {
    public TimeSpan calculateETA(Route route) {
        // Calculation logic for ETA based on route distance and speed assumptions
        return route.getEstimatedTime();
    }
}
