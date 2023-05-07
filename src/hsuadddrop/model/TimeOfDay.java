package hsuadddrop.model;

public enum TimeOfDay {
    MORNING("Morning"),
    AFTERNOON("Afternoon"),
    EVENING("Evening");

    private final String displayName;

    TimeOfDay(String displayName) {
        this.displayName = displayName;
    }

    public static TimeOfDay fromDisplayName(String displayName) {
        for (TimeOfDay timeOfDay : TimeOfDay.values()) {
            if (timeOfDay.displayName.equals(displayName)) {
                return timeOfDay;
            }
        }
        throw new IllegalArgumentException("No enum constant with display name: " + displayName);
    }
    @Override
    public String toString() {
        return displayName;
    }
}
