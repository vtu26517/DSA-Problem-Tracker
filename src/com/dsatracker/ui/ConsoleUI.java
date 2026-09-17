package com.dsatracker.ui;

import com.dsatracker.filter.ProblemFilter;
import com.dsatracker.model.Difficulty;
import com.dsatracker.model.Problem;
import com.dsatracker.model.Status;
import com.dsatracker.model.Topic;
import com.dsatracker.service.ProblemService;
import com.dsatracker.service.StatisticsService;
import com.dsatracker.sort.ProblemSorter;
import com.dsatracker.util.DemoDataGenerator;

import java.time.LocalDate;
import java.util.*;

public class ConsoleUI {
    private final ProblemService problemService;
    private final Scanner scanner;
    private final boolean enableAnsi;

    public ConsoleUI(ProblemService problemService) {
        this.problemService = problemService;
        this.scanner = new Scanner(System.in);
        this.enableAnsi = checkAnsiSupport();
    }

    private boolean checkAnsiSupport() {
        String os = System.getProperty("os.name").toLowerCase();
        return !os.contains("win") || System.getenv("WT_SESSION") != null || System.getenv("TERM") != null;
    }

    public void start() {
        printBanner();

        boolean running = true;
        while (running) {
            printMainMenu();
            String input = readInput("Enter your choice (1-10): ");
            switch (input.trim()) {
                case "1":
                    handleAddProblem();
                    break;
                case "2":
                    handleViewAllProblems();
                    break;
                case "3":
                    handleFilterProblems();
                    break;
                case "4":
                    handleSortProblems();
                    break;
                case "5":
                    handleSearchProblems();
                    break;
                case "6":
                    handleEditProblem();
                    break;
                case "7":
                    handleDeleteProblem();
                    break;
                case "8":
                    handleViewStatistics();
                    break;
                case "9":
                    handleLoadDemoData();
                    break;
                case "10":
                    running = false;
                    System.out.println("\nThank you for using DSA Problem Tracker. Keep practicing & happy coding!\n");
                    break;
                default:
                    System.out.println("\n[!] Invalid option. Please enter a number between 1 and 10.\n");
            }
        }
    }

    private void printBanner() {
        System.out.println("=========================================================================================");
        System.out.println("                     DSA PROBLEM TRACKER & PERFORMANCE ANALYZER                          ");
        System.out.println("                Java Collections Framework | OOP Architecture | Analytics               ");
        System.out.println("=========================================================================================");
    }

    private void printMainMenu() {
        System.out.println("\n---------------------------- MAIN MENU ----------------------------");
        System.out.println(" [1]  ➕ Add New Problem");
        System.out.println(" [2]  📋 View All Solved Problems");
        System.out.println(" [3]  🔍 Filter Problems (Topic / Difficulty / Status / Tag)");
        System.out.println(" [4]  🔀 Sort Problems (Date / Difficulty / Time / Title)");
        System.out.println(" [5]  🔎 Search Problems by Keyword");
        System.out.println(" [6]  ✏️  Edit Problem Details");
        System.out.println(" [7]  🗑️  Delete Problem");
        System.out.println(" [8]  📊 View Statistics & Performance Dashboard");
        System.out.println(" [9]  🚀 Load Sample Demo Problems");
        System.out.println(" [10] ❌ Exit Application");
        System.out.println("-------------------------------------------------------------------");
    }

    private void handleAddProblem() {
        System.out.println("\n--- ➕ ADD NEW DSA PROBLEM ---");
        String title = readInput("Enter Problem Title: ");
        if (title.isEmpty()) {
            System.out.println("Title cannot be empty. Aborted.");
            return;
        }

        Topic topic = selectTopicPrompt();
        Difficulty difficulty = selectDifficultyPrompt();
        Status status = selectStatusPrompt();

        String platform = readInput("Enter Platform (e.g. LeetCode, HackerRank, GeeksforGeeks) [LeetCode]: ");
        if (platform.isEmpty()) platform = "LeetCode";

        int timeTaken = readIntInput("Enter Time Taken in Minutes (e.g. 20): ", 15);
        String notes = readInput("Enter Solution Notes / Approach: ");
        String url = readInput("Enter Problem / Solution Link: ");

        String tagsInput = readInput("Enter Tags (comma separated, e.g. dp, sliding-window, hashmap): ");
        Set<String> tags = new HashSet<>();
        if (!tagsInput.isEmpty()) {
            for (String t : tagsInput.split(",")) {
                if (!t.trim().isEmpty()) tags.add(t.trim().toLowerCase());
            }
        }

        Problem problem = new Problem(0, title, topic, difficulty, status, platform, timeTaken, notes, url, tags, LocalDate.now());
        problemService.addProblem(problem);
        System.out.println("\n✔ Problem successfully added! Assigned ID: " + problem.getId());
    }

    private void handleViewAllProblems() {
        System.out.println("\n--- 📋 ALL DSA PROBLEMS ---");
        List<Problem> problems = problemService.getAllProblems();
        System.out.print(TableFormatter.formatProblemTable(problems, enableAnsi));
    }

    private void handleFilterProblems() {
        System.out.println("\n--- 🔍 FILTER PROBLEMS ---");
        System.out.println("1. Filter by Topic");
        System.out.println("2. Filter by Difficulty");
        System.out.println("3. Filter by Status");
        System.out.println("4. Filter by Tag");
        System.out.println("5. Filter by Platform");

        String choice = readInput("Select Filter Sub-Option (1-5): ");
        ProblemFilter.Criteria criteria = new ProblemFilter.Criteria();

        switch (choice.trim()) {
            case "1":
                criteria.setTopic(selectTopicPrompt());
                break;
            case "2":
                criteria.setDifficulty(selectDifficultyPrompt());
                break;
            case "3":
                criteria.setStatus(selectStatusPrompt());
                break;
            case "4":
                String tag = readInput("Enter Tag to search: ");
                criteria.setTag(tag);
                break;
            case "5":
                String platform = readInput("Enter Platform name: ");
                criteria.setPlatform(platform);
                break;
            default:
                System.out.println("Invalid selection.");
                return;
        }

        List<Problem> filtered = problemService.getFilteredProblems(criteria);
        System.out.println("\n--- FILTER RESULTS ---");
        System.out.print(TableFormatter.formatProblemTable(filtered, enableAnsi));
    }

    private void handleSortProblems() {
        System.out.println("\n--- 🔀 SORT PROBLEMS ---");
        System.out.println("1. Date Solved (Newest First)");
        System.out.println("2. Date Solved (Oldest First)");
        System.out.println("3. Difficulty (Easy -> Medium -> Hard)");
        System.out.println("4. Difficulty (Hard -> Medium -> Easy)");
        System.out.println("5. Time Spent (Shortest First)");
        System.out.println("6. Time Spent (Longest First)");
        System.out.println("7. Title (A - Z)");
        System.out.println("8. Topic (Alphabetical)");

        String choice = readInput("Select Sorting Preference (1-8): ");
        ProblemSorter.SortOption option;
        switch (choice.trim()) {
            case "1": option = ProblemSorter.SortOption.DATE_DESC; break;
            case "2": option = ProblemSorter.SortOption.DATE_ASC; break;
            case "3": option = ProblemSorter.SortOption.DIFFICULTY_ASC; break;
            case "4": option = ProblemSorter.SortOption.DIFFICULTY_DESC; break;
            case "5": option = ProblemSorter.SortOption.TIME_ASC; break;
            case "6": option = ProblemSorter.SortOption.TIME_DESC; break;
            case "7": option = ProblemSorter.SortOption.TITLE_AZ; break;
            case "8": option = ProblemSorter.SortOption.TOPIC_AZ; break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        List<Problem> sorted = problemService.getSortedProblems(option);
        System.out.println("\n--- SORTED PROBLEMS (" + option.getLabel() + ") ---");
        System.out.print(TableFormatter.formatProblemTable(sorted, enableAnsi));
    }

    private void handleSearchProblems() {
        System.out.println("\n--- 🔎 SEARCH PROBLEMS ---");
        String kw = readInput("Enter title or keyword in notes: ");
        if (kw.isEmpty()) return;
        List<Problem> results = problemService.searchProblems(kw);
        System.out.println("\n--- SEARCH RESULTS FOR '" + kw + "' ---");
        System.out.print(TableFormatter.formatProblemTable(results, enableAnsi));
    }

    private void handleEditProblem() {
        System.out.println("\n--- ✏️ EDIT PROBLEM DETAILS ---");
        int id = readIntInput("Enter Problem ID to edit: ", -1);
        Optional<Problem> opt = problemService.getProblemById(id);
        if (!opt.isPresent()) {
            System.out.println("Problem with ID " + id + " not found.");
            return;
        }

        Problem p = opt.get();
        System.out.println("Editing Problem: " + p.getTitle());
        String newTitle = readInput("Enter new Title [" + p.getTitle() + "]: ");
        if (!newTitle.isEmpty()) p.setTitle(newTitle);

        System.out.println("Change Topic? (Current: " + p.getTopic().getDisplayName() + ")");
        if (readYesNo("Update Topic? (y/n): ")) {
            p.setTopic(selectTopicPrompt());
        }

        System.out.println("Change Difficulty? (Current: " + p.getDifficulty().getDisplayName() + ")");
        if (readYesNo("Update Difficulty? (y/n): ")) {
            p.setDifficulty(selectDifficultyPrompt());
        }

        System.out.println("Change Status? (Current: " + p.getStatus().getDisplayName() + ")");
        if (readYesNo("Update Status? (y/n): ")) {
            p.setStatus(selectStatusPrompt());
        }

        String newPlatform = readInput("Enter Platform [" + p.getPlatform() + "]: ");
        if (!newPlatform.isEmpty()) p.setPlatform(newPlatform);

        String newTimeStr = readInput("Enter Time Spent in Minutes [" + p.getTimeTakenMinutes() + "]: ");
        if (!newTimeStr.isEmpty()) {
            try { p.setTimeTakenMinutes(Integer.parseInt(newTimeStr)); } catch (Exception ignored) {}
        }

        String newNotes = readInput("Enter Notes [" + p.getNotes() + "]: ");
        if (!newNotes.isEmpty()) p.setNotes(newNotes);

        problemService.updateProblem(p);
        System.out.println("✔ Problem updated successfully!");
    }

    private void handleDeleteProblem() {
        System.out.println("\n--- 🗑️ DELETE PROBLEM ---");
        int id = readIntInput("Enter Problem ID to delete: ", -1);
        if (id <= 0) return;

        if (readYesNo("Are you sure you want to delete problem ID " + id + "? (y/n): ")) {
            boolean deleted = problemService.deleteProblem(id);
            if (deleted) {
                System.out.println("✔ Problem ID " + id + " deleted.");
            } else {
                System.out.println("Problem ID " + id + " not found.");
            }
        }
    }

    private void handleViewStatistics() {
        System.out.println("\n=========================================================================================");
        System.out.println("                       📊 PERFORMANCE & TOPIC-WISE ANALYTICS REPORT                       ");
        System.out.println("=========================================================================================");

        StatisticsService.SummaryReport report = problemService.getStatistics();
        if (report.getTotalProblems() == 0) {
            System.out.println("  No problems recorded yet. Add problems or load sample demo data first.");
            return;
        }

        System.out.printf("  Total Problems Recorded : %d\n", report.getTotalProblems());
        System.out.printf("  ✔ Solved                : %d\n", report.getSolvedCount());
        System.out.printf("  ⏳ Attempted             : %d\n", report.getAttemptedCount());
        System.out.printf("  🔄 Needs Revisit        : %d\n", report.getRevisitCount());
        System.out.printf("  Progress Rate           : %.1f%%\n", report.getSolvedPercentage());
        printProgressBar(report.getSolvedPercentage());

        System.out.printf("  Average Time / Problem  : %.1f mins\n", report.getAverageTimeMinutes());
        if (report.getMostPracticedTopic() != null) {
            System.out.printf("  Top Practiced Topic     : %s\n", report.getMostPracticedTopic().getDisplayName());
        }

        System.out.println("\n-- 🎯 Difficulty Breakdown --");
        for (Map.Entry<Difficulty, Long> e : report.getDifficultyCounts().entrySet()) {
            long count = e.getValue();
            double pct = report.getTotalProblems() > 0 ? (count * 100.0) / report.getTotalProblems() : 0;
            System.out.printf("  %-8s : %2d problem(s) (%5.1f%%)\n", e.getKey().getDisplayName(), count, pct);
        }

        System.out.println("\n-- 📚 Topic Breakdown & Time Spent --");
        Map<Topic, Long> topicCounts = report.getTopicCounts();
        Map<Topic, Double> topicAvgTimes = report.getTopicAverageTime();
        for (Map.Entry<Topic, Long> e : topicCounts.entrySet()) {
            Topic topic = e.getKey();
            long count = e.getValue();
            double avgT = topicAvgTimes.getOrDefault(topic, 0.0);
            System.out.printf("  %-30s : %2d problem(s) | Avg Time: %4.1f mins\n",
                    topic.getDisplayName(), count, avgT);
        }

        System.out.println("\n-- 🌐 Platform Distribution --");
        for (Map.Entry<String, Long> e : report.getPlatformCounts().entrySet()) {
            System.out.printf("  %-15s : %d problem(s)\n", e.getKey(), e.getValue());
        }
        System.out.println("=========================================================================================\n");
    }

    private void handleLoadDemoData() {
        System.out.println("\n--- 🚀 LOAD DEMO SAMPLE PROBLEMS ---");
        List<Problem> demo = DemoDataGenerator.getSampleProblems();
        problemService.loadDemoData(demo);
        System.out.println("✔ Successfully loaded " + demo.size() + " sample DSA problems into storage!");
    }

    private Topic selectTopicPrompt() {
        System.out.println("\nSelect Topic Category:");
        Topic[] values = Topic.values();
        for (int i = 0; i < values.length; i++) {
            System.out.printf(" [%2d] %s\n", i + 1, values[i].getDisplayName());
        }
        int idx = readIntInput("Enter Topic choice (1-" + values.length + "): ", 1);
        if (idx < 1 || idx > values.length) return Topic.OTHER;
        return values[idx - 1];
    }

    private Difficulty selectDifficultyPrompt() {
        System.out.println("\nSelect Difficulty Level:");
        Difficulty[] values = Difficulty.values();
        for (int i = 0; i < values.length; i++) {
            System.out.printf(" [%d] %s\n", i + 1, values[i].getDisplayName());
        }
        int idx = readIntInput("Enter Difficulty choice (1-" + values.length + "): ", 1);
        if (idx < 1 || idx > values.length) return Difficulty.EASY;
        return values[idx - 1];
    }

    private Status selectStatusPrompt() {
        System.out.println("\nSelect Problem Status:");
        Status[] values = Status.values();
        for (int i = 0; i < values.length; i++) {
            System.out.printf(" [%d] %s\n", i + 1, values[i].getDisplayName());
        }
        int idx = readIntInput("Enter Status choice (1-" + values.length + "): ", 1);
        if (idx < 1 || idx > values.length) return Status.SOLVED;
        return values[idx - 1];
    }

    private void printProgressBar(double percentage) {
        int width = 30;
        int filled = (int) Math.round((percentage / 100.0) * width);
        StringBuilder sb = new StringBuilder("  Progress: [");
        for (int i = 0; i < width; i++) {
            if (i < filled) sb.append("=");
            else if (i == filled) sb.append(">");
            else sb.append(" ");
        }
        sb.append(String.format("] %.1f%%\n", percentage));
        System.out.println(sb.toString());
    }

    private String readInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int readIntInput(String prompt, int defaultValue) {
        System.out.print(prompt);
        String line = scanner.nextLine().trim();
        try {
            return Integer.parseInt(line);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private boolean readYesNo(String prompt) {
        String input = readInput(prompt);
        return input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes");
    }
}
