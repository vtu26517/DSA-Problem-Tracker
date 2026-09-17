package com.dsatracker.model;

public enum Difficulty {
    EASY("Easy", 1, "\u001B[32m"),     // Green
    MEDIUM("Medium", 2, "\u001B[33m"), // Yellow
    HARD("Hard", 3, "\u001B[31m");     // Red

    private final String displayName;
    private final int level;
    private final String ansiColor;

    Difficulty(String displayName, int level, String ansiColor) {
        this.displayName = displayName;
        this.level = level;
        this.ansiColor = ansiColor;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getLevel() {
        return level;
    }

    public String getAnsiColor() {
        return ansiColor;
    }

    public static Difficulty fromString(String text) {
        if (text == null) return EASY;
        for (Difficulty d : Difficulty.values()) {
            if (d.name().equalsIgnoreCase(text.trim()) || d.displayName.equalsIgnoreCase(text.trim())) {
                return d;
            }
        }
        return EASY;
    }
}
