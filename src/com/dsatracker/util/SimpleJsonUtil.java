package com.dsatracker.util;

import com.dsatracker.model.Difficulty;
import com.dsatracker.model.Problem;
import com.dsatracker.model.Status;
import com.dsatracker.model.Topic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimpleJsonUtil {

    public static String toJson(List<Problem> problems) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < problems.size(); i++) {
            Problem p = problems.get(i);
            sb.append("  {\n");
            sb.append("    \"id\": ").append(p.getId()).append(",\n");
            sb.append("    \"title\": \"").append(escapeJson(p.getTitle())).append("\",\n");
            sb.append("    \"topic\": \"").append(p.getTopic().name()).append("\",\n");
            sb.append("    \"difficulty\": \"").append(p.getDifficulty().name()).append("\",\n");
            sb.append("    \"status\": \"").append(p.getStatus().name()).append("\",\n");
            sb.append("    \"platform\": \"").append(escapeJson(p.getPlatform())).append("\",\n");
            sb.append("    \"timeTakenMinutes\": ").append(p.getTimeTakenMinutes()).append(",\n");
            sb.append("    \"notes\": \"").append(escapeJson(p.getNotes())).append("\",\n");
            sb.append("    \"solutionUrl\": \"").append(escapeJson(p.getSolutionUrl())).append("\",\n");
            sb.append("    \"tags\": [");
            Set<String> tags = p.getTags();
            int tIdx = 0;
            for (String tag : tags) {
                sb.append("\"").append(escapeJson(tag)).append("\"");
                if (++tIdx < tags.size()) sb.append(", ");
            }
            sb.append("],\n");
            sb.append("    \"dateSolved\": \"").append(p.getDateSolved().toString()).append("\"\n");
            sb.append("  }");
            if (i < problems.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("]");
        return sb.toString();
    }

    public static List<Problem> parseJson(String json) {
        List<Problem> problems = new ArrayList<>();
        if (json == null || json.trim().isEmpty()) {
            return problems;
        }

        String content = json.trim();
        if (!content.startsWith("[") || !content.endsWith("]")) {
            return problems;
        }

        // Split individual JSON objects
        List<String> objectStrings = extractJsonObjects(content);
        for (String objStr : objectStrings) {
            try {
                Problem p = parseSingleProblem(objStr);
                if (p != null) {
                    problems.add(p);
                }
            } catch (Exception e) {
                System.err.println("Warning: failed to parse problem object: " + e.getMessage());
            }
        }

        return problems;
    }

    private static List<String> extractJsonObjects(String content) {
        List<String> objects = new ArrayList<>();
        int depth = 0;
        int start = -1;

        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (c == '{') {
                if (depth == 0) {
                    start = i;
                }
                depth++;
            } else if (c == '}') {
                depth--;
                if (depth == 0 && start != -1) {
                    objects.add(content.substring(start, i + 1));
                    start = -1;
                }
            }
        }
        return objects;
    }

    private static Problem parseSingleProblem(String jsonObject) {
        int id = extractInt(jsonObject, "id");
        String title = extractString(jsonObject, "title");
        String topicStr = extractString(jsonObject, "topic");
        String diffStr = extractString(jsonObject, "difficulty");
        String statusStr = extractString(jsonObject, "status");
        String platform = extractString(jsonObject, "platform");
        int time = extractInt(jsonObject, "timeTakenMinutes");
        String notes = extractString(jsonObject, "notes");
        String solutionUrl = extractString(jsonObject, "solutionUrl");
        Set<String> tags = extractTags(jsonObject);
        String dateStr = extractString(jsonObject, "dateSolved");

        Topic topic = Topic.fromString(topicStr);
        Difficulty difficulty = Difficulty.fromString(diffStr);
        Status status = Status.fromString(statusStr);
        LocalDate dateSolved = LocalDate.now();
        if (dateStr != null && !dateStr.isEmpty()) {
            try {
                dateSolved = LocalDate.parse(dateStr);
            } catch (Exception ignored) {}
        }

        return new Problem(id, title, topic, difficulty, status, platform, time, notes, solutionUrl, tags, dateSolved);
    }

    private static String extractString(String json, String key) {
        String pattern = "\"" + key + "\":";
        int idx = json.indexOf(pattern);
        if (idx == -1) return "";
        int startQuote = json.indexOf("\"", idx + pattern.length());
        if (startQuote == -1) return "";
        StringBuilder sb = new StringBuilder();
        boolean escaped = false;
        for (int i = startQuote + 1; i < json.length(); i++) {
            char c = json.charAt(i);
            if (escaped) {
                if (c == 'n') sb.append('\n');
                else if (c == 't') sb.append('\t');
                else sb.append(c);
                escaped = false;
            } else if (c == '\\') {
                escaped = true;
            } else if (c == '"') {
                break;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static int extractInt(String json, String key) {
        String pattern = "\"" + key + "\":";
        int idx = json.indexOf(pattern);
        if (idx == -1) return 0;
        int start = idx + pattern.length();
        while (start < json.length() && (Character.isWhitespace(json.charAt(start)))) {
            start++;
        }
        int end = start;
        while (end < json.length() && (Character.isDigit(json.charAt(end)) || json.charAt(end) == '-')) {
            end++;
        }
        try {
            return Integer.parseInt(json.substring(start, end));
        } catch (Exception e) {
            return 0;
        }
    }

    private static Set<String> extractTags(String json) {
        Set<String> tags = new HashSet<>();
        String pattern = "\"tags\":";
        int idx = json.indexOf(pattern);
        if (idx == -1) return tags;
        int startBracket = json.indexOf("[", idx + pattern.length());
        int endBracket = json.indexOf("]", startBracket != -1 ? startBracket : idx);
        if (startBracket == -1 || endBracket == -1) return tags;

        String arrayContent = json.substring(startBracket + 1, endBracket);
        String[] items = arrayContent.split(",");
        for (String item : items) {
            String clean = item.trim().replaceAll("^\"|\"$", "").trim();
            if (!clean.isEmpty()) {
                tags.add(clean.toLowerCase());
            }
        }
        return tags;
    }

    private static String escapeJson(String raw) {
        if (raw == null) return "";
        return raw.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
