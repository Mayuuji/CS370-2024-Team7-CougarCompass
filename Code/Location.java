public class Location {
    private String name;
    private Coordinates coordinates;
    private AccessibilityOptions accessibilityOptions;

    public Location(String name, Coordinates coordinates, AccessibilityOptions accessibilityOptions) {
        this.name = name;
        this.coordinates = coordinates;
        this.accessibilityOptions = accessibilityOptions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public AccessibilityOptions getAccessibilityOptions() {
        return accessibilityOptions;
    }

    public void setAccessibilityOptions(AccessibilityOptions accessibilityOptions) {
        this.accessibilityOptions = accessibilityOptions;
    }
}

