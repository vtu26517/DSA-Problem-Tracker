package com.dsatracker.model;

public enum Status {
    SOLVED("Solved", "\u001B[32m✔ Solved\u001B[0m"),
    ATTEMPTED("Attempted", "\u001B[33m⏳ Attempted\u001B[0m"),
    REVISIT("Needs Revisit", "\u001B[35m🔄 Revisit\u001B[0m");

    private final String displayName;
    private final String formattedDisplay;

    Status(String displayName, String formattedDisplay) {
        this.displayName = displayName;
        this.formattedDisplay = formattedDisplay;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFormattedDisplay() {
        return formattedDisplay;
    }

    public static Status fromString(String text) {
        if (text == null) return SOLVED;
        for (Status s : Status.values()) {
            if (s.name().equalsIgnoreCase(text.trim()) || s.displayName.equalsIgnoreCase(text.trim())) {
                return s;
            }
        }
        return SOLVED;
    }
}
