/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hsuadddrop.model;

/**
 *
 * @author lukaon
 */
public enum Weekday {
    MONDAY("Mon"),
    TUESDAY("Tue"),
    WEDNESDAY("Wed"),
    THURSDAY("Thu"),
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
