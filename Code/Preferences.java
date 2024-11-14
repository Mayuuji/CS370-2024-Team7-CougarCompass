public class Preferences {
    private AccessibilityOptions accessibilityOption;
    private boolean includeBreaks;
    private Location breakLocations;

    public Preferences(AccessibilityOptions accessibilityOption, boolean includeBreaks, Location breakLocations) {
        this.accessibilityOption = accessibilityOption;
        this.includeBreaks = includeBreaks;
        this.breakLocations = breakLocations;
    }

    public AccessibilityOptions getAccessibilityOption() {
        return accessibilityOption;
    }

    public void setAccessibilityOption(AccessibilityOptions accessibilityOption) {
        this.accessibilityOption = accessibilityOption;
    }

    public boolean isIncludeBreaks() {
        return includeBreaks;
    }

    public void toggleIncludeBreak(boolean includeBreak) {
        this.includeBreaks = includeBreak;
    }

    public Location getBreakLocations() {
        return breakLocations;
    }

    // Additional setters if necessary
}

