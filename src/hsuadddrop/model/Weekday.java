package hsuadddrop.model;
public enum Weekday {
    MONDAY("Mon"),
    TUESDAY("Tue"),
    WEDNESDAY("Wed"),
    THURSDAY("Thur"),
    FRIDAY("Fri"),
    SATURDAY("Sat");

    private final String displayName;

    Weekday(String displayName) {
        this.displayName = displayName;
    }
    
    public static Weekday fromDisplayName(String displayName) {
        for (Weekday weekday : Weekday.values()) {
            if (weekday.displayName.equals(displayName)) {
                return weekday;
            }
        }
        throw new IllegalArgumentException("No enum constant with display name: " + displayName);
    }
    
    @Override
    public String toString() {
        return displayName;
    }

}
