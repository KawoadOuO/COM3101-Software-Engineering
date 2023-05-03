/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hsuadddrop.model;

/**
 *
 * @author lukaon
 */
public enum TimeOfDay {
    MORNING("Morning"),
    AFTERNOON("Afternoon"),
    EVENING("Evening");

    private final String displayName;

    public static TimeOfDay fromDisplayName(String displayName) {
        for (TimeOfDay timeOfDay : TimeOfDay.values()) {
            if (timeOfDay.displayName.equals(displayName)) {
                return timeOfDay;
            }
        }
        throw new IllegalArgumentException("No enum constant with display name: " + displayName);
    }

    TimeOfDay(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
