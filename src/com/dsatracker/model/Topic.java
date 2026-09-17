package com.dsatracker.model;

public enum Topic {
    ARRAYS("Arrays & Vectors"),
    STRINGS("Strings"),
    LINKED_LIST("Linked Lists"),
    STACKS_QUEUES("Stacks & Queues"),
    TREES("Trees & Binary Search Trees"),
    GRAPHS("Graphs & BFS/DFS"),
    DYNAMIC_PROGRAMMING("Dynamic Programming"),
    GREEDY("Greedy Algorithms"),
    HEAP_PRIORITY_QUEUE("Heap & Priority Queue"),
    SORTING_SEARCHING("Sorting & Searching"),
    TWO_POINTERS("Two Pointers & Sliding Window"),
    BACKTRACKING("Backtracking"),
    BIT_MANIPULATION("Bit Manipulation"),
    MATH_GEOMETRY("Math & Geometry"),
    OTHER("Other Topics");

    private final String displayName;

    Topic(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Topic fromString(String text) {
        if (text == null) return OTHER;
        String clean = text.trim();
        for (Topic t : Topic.values()) {
            if (t.name().equalsIgnoreCase(clean) || t.displayName.equalsIgnoreCase(clean)) {
                return t;
            }
        }
        // Match partial
        for (Topic t : Topic.values()) {
            if (t.displayName.toLowerCase().contains(clean.toLowerCase())) {
                return t;
            }
        }
        return OTHER;
    }
}
