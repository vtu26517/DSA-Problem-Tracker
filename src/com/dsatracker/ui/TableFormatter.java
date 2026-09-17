package com.dsatracker.ui;

import com.dsatracker.model.Problem;

import java.util.List;

public class TableFormatter {

    public static String formatProblemTable(List<Problem> problems, boolean useColor) {
        if (problems == null || problems.isEmpty()) {
            return "  \u001B[33mNo problems found matching the criteria.\u001B[0m\n";
        }

        int idWidth = 4;
        int titleWidth = 32;
        int topicWidth = 22;
        int diffWidth = 10;
        int statusWidth = 14;
        int platWidth = 14;
        int timeWidth = 8;
        int dateWidth = 12;

        StringBuilder sb = new StringBuilder();
        String lineSeparator = "+" + "-".repeat(idWidth + 2) +
                "+" + "-".repeat(titleWidth + 2) +
                "+" + "-".repeat(topicWidth + 2) +
                "+" + "-".repeat(diffWidth + 2) +
                "+" + "-".repeat(statusWidth + 2) +
                "+" + "-".repeat(platWidth + 2) +
                "+" + "-".repeat(timeWidth + 2) +
                "+" + "-".repeat(dateWidth + 2) + "+\n";

        sb.append(lineSeparator);
        sb.append(String.format("| %-" + idWidth + "s | %-" + titleWidth + "s | %-" + topicWidth + "s | %-" + diffWidth + "s | %-" + statusWidth + "s | %-" + platWidth + "s | %-" + timeWidth + "s | %-" + dateWidth + "s |\n",
                "ID", "TITLE", "TOPIC", "DIFFICULTY", "STATUS", "PLATFORM", "TIME(m)", "DATE"));
        sb.append(lineSeparator);

        for (Problem p : problems) {
            String title = truncate(p.getTitle(), titleWidth);
            String topic = truncate(p.getTopic().getDisplayName(), topicWidth);
            String platform = truncate(p.getPlatform(), platWidth);
            String time = p.getTimeTakenMinutes() + " m";
            String date = p.getDateSolved() != null ? p.getDateSolved().toString() : "N/A";

            String diffDisplay = useColor ? p.getDifficulty().getAnsiColor() + String.format("%-" + diffWidth + "s", p.getDifficulty().getDisplayName()) + "\u001B[0m"
                    : String.format("%-" + diffWidth + "s", p.getDifficulty().getDisplayName());

            String statusDisplay = useColor ? String.format("%-" + statusWidth + "s", p.getStatus().getDisplayName())
                    : String.format("%-" + statusWidth + "s", p.getStatus().getDisplayName());

            sb.append(String.format("| %-" + idWidth + "d | %-" + titleWidth + "s | %-" + topicWidth + "s | %s | %s | %-" + platWidth + "s | %-" + timeWidth + "s | %-" + dateWidth + "s |\n",
                    p.getId(), title, topic, diffDisplay, statusDisplay, platform, time, date));
        }

        sb.append(lineSeparator);
        sb.append(String.format(" Total Count: %d problem(s)\n", problems.size()));
        return sb.toString();
    }

    private static String truncate(String text, int maxLen) {
        if (text == null) return "";
        if (text.length() <= maxLen) return text;
        return text.substring(0, maxLen - 3) + "...";
    }
}
