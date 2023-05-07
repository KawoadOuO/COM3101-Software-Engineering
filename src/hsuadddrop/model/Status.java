package hsuadddrop.model;

public enum Status {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public static Status fromDisplayName(String displayName) {
        for (Status status : Status.values()) {
            if (status.displayName.equals(displayName)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No enum constant with display name: " + displayName);
    }
    @Override
    public String toString() {
        return displayName;
    }
}
