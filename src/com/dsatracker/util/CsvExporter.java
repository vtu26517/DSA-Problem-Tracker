package com.dsatracker.util;

import com.dsatracker.model.Problem;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvExporter {

    public static boolean exportToCsv(List<Problem> problems, String filePath) {
        if (problems == null || filePath == null) return false;

        try (FileWriter writer = new FileWriter(filePath)) {
            // Write CSV Header
            writer.write("ID,Title,Topic,Difficulty,Status,Platform,TimeTakenMinutes,DateSolved,Tags,Notes,SolutionUrl\n");

            for (Problem p : problems) {
                StringBuilder sb = new StringBuilder();
                sb.append(p.getId()).append(",");
                sb.append(escapeCsv(p.getTitle())).append(",");
                sb.append(escapeCsv(p.getTopic().getDisplayName())).append(",");
                sb.append(escapeCsv(p.getDifficulty().getDisplayName())).append(",");
                sb.append(escapeCsv(p.getStatus().getDisplayName())).append(",");
                sb.append(escapeCsv(p.getPlatform())).append(",");
                sb.append(p.getTimeTakenMinutes()).append(",");
                sb.append(p.getDateSolved() != null ? p.getDateSolved().toString() : "").append(",");
                sb.append(escapeCsv(String.join(";", p.getTags()))).append(",");
                sb.append(escapeCsv(p.getNotes())).append(",");
                sb.append(escapeCsv(p.getSolutionUrl())).append("\n");

                writer.write(sb.toString());
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
            return false;
        }
    }

    private static String escapeCsv(String value) {
        if (value == null) return "\"\"";
        String clean = value.replace("\"", "\"\"");
        if (clean.contains(",") || clean.contains("\n") || clean.contains("\"")) {
            return "\"" + clean + "\"";
        }
        return clean;
    }
}
