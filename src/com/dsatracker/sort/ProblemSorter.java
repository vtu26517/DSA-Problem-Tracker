package com.dsatracker.sort;

import com.dsatracker.model.Problem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProblemSorter {

    public enum SortOption {
        DATE_DESC("Date Solved (Newest First)"),
        DATE_ASC("Date Solved (Oldest First)"),
        DIFFICULTY_ASC("Difficulty (Easy -> Hard)"),
        DIFFICULTY_DESC("Difficulty (Hard -> Easy)"),
        TIME_ASC("Time Taken (Shortest First)"),
        TIME_DESC("Time Taken (Longest First)"),
        TITLE_AZ("Title (A-Z)"),
        TOPIC_AZ("Topic (Alphabetical)");

        private final String label;

        SortOption(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public static List<Problem> sort(List<Problem> problems, SortOption option) {
        List<Problem> sortedList = new ArrayList<>(problems);
        if (option == null) return sortedList;

        Comparator<Problem> comparator;

        switch (option) {
            case DATE_DESC:
                comparator = Comparator.comparing(Problem::getDateSolved, Comparator.nullsLast(Comparator.reverseOrder()))
                        .thenComparing(Problem::getId, Comparator.reverseOrder());
                break;
            case DATE_ASC:
                comparator = Comparator.comparing(Problem::getDateSolved, Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(Problem::getId);
                break;
            case DIFFICULTY_ASC:
                comparator = Comparator.comparingInt((Problem p) -> p.getDifficulty().getLevel())
                        .thenComparing(Problem::getTitle);
                break;
            case DIFFICULTY_DESC:
                comparator = Comparator.comparingInt((Problem p) -> p.getDifficulty().getLevel()).reversed()
                        .thenComparing(Problem::getTitle);
                break;
            case TIME_ASC:
                comparator = Comparator.comparingInt(Problem::getTimeTakenMinutes)
                        .thenComparing(Problem::getTitle);
                break;
            case TIME_DESC:
                comparator = Comparator.comparingInt(Problem::getTimeTakenMinutes).reversed()
                        .thenComparing(Problem::getTitle);
                break;
            case TITLE_AZ:
                comparator = Comparator.comparing(Problem::getTitle, String.CASE_INSENSITIVE_ORDER);
                break;
            case TOPIC_AZ:
                comparator = Comparator.comparing((Problem p) -> p.getTopic().getDisplayName(), String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(Problem::getTitle);
                break;
            default:
                comparator = Comparator.comparingInt(Problem::getId);
                break;
        }

        Collections.sort(sortedList, comparator);
        return sortedList;
    }
}
