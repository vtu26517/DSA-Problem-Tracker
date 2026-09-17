package com.dsatracker;

import com.dsatracker.filter.ProblemFilter;
import com.dsatracker.model.Difficulty;
import com.dsatracker.model.Problem;
import com.dsatracker.model.Status;
import com.dsatracker.model.Topic;
import com.dsatracker.repository.FileProblemRepository;
import com.dsatracker.repository.ProblemRepository;
import com.dsatracker.service.ProblemService;
import com.dsatracker.service.StatisticsService;
import com.dsatracker.sort.ProblemSorter;
import com.dsatracker.util.DemoDataGenerator;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

public class TestRunner {
    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("          DSA PROBLEM TRACKER - AUTOMATED TEST SUITE      ");
        System.out.println("==========================================================");

        testProblemCreationAndEncapsulation();
        testRepositoryAndPersistence();
        testCollectionsSorting();
        testCollectionsFiltering();
        testStatisticsCalculations();
        testSearchAndKeywordMatching();

        System.out.println("\n----------------------------------------------------------");
        System.out.printf("  TEST RESULTS: %d PASSED, %d FAILED\n", testsPassed, testsFailed);
        System.out.println("----------------------------------------------------------");

        if (testsFailed > 0) {
            System.exit(1);
        }
    }

    private static void assertCondition(String testName, boolean condition) {
        if (condition) {
            System.out.printf("  [PASS] %s\n", testName);
            testsPassed++;
        } else {
            System.out.printf("  [FAIL] %s\n", testName);
            testsFailed++;
        }
    }

    private static void testProblemCreationAndEncapsulation() {
        System.out.println("\n[1] Testing Domain Model Encapsulation...");
        Problem p = new Problem(101, "Test Binary Search", Topic.SORTING_SEARCHING,
                Difficulty.EASY, Status.SOLVED, "LeetCode", 15, "Used mid calculation",
                "https://leetcode.com", new HashSet<>(Arrays.asList("binary-search", "array")), LocalDate.now());

        assertCondition("Problem ID Getter", p.getId() == 101);
        assertCondition("Title Getter", p.getTitle().equals("Test Binary Search"));
        assertCondition("Topic Enum Match", p.getTopic() == Topic.SORTING_SEARCHING);
        assertCondition("Difficulty Enum Match", p.getDifficulty() == Difficulty.EASY);
        assertCondition("Tags Immutable Copy", p.getTags().contains("binary-search"));
    }

    private static void testRepositoryAndPersistence() {
        System.out.println("\n[2] Testing Repository Persistence & File Operations...");
        String tempFilePath = "data/test_problems.json";
        File file = new File(tempFilePath);
        if (file.exists()) file.delete();

        ProblemRepository repo = new FileProblemRepository(tempFilePath);
        ProblemService service = new ProblemService(repo);

        Problem p1 = new Problem(0, "Subarray Sum Equals K", Topic.ARRAYS, Difficulty.MEDIUM,
                Status.SOLVED, "LeetCode", 30, "Prefix sum hashmap", "",
                new HashSet<>(Collections.singletonList("prefix-sum")), LocalDate.now());
        service.addProblem(p1);

        assertCondition("Auto-generated ID", p1.getId() > 0);
        assertCondition("Find by ID", service.getProblemById(p1.getId()).isPresent());

        // Test persistence re-load
        ProblemRepository repo2 = new FileProblemRepository(tempFilePath);
        ProblemService service2 = new ProblemService(repo2);
        assertCondition("Persistence Reload Count", service2.getAllProblems().size() == 1);
        assertCondition("Persistence Title Reload", service2.getAllProblems().get(0).getTitle().equals("Subarray Sum Equals K"));

        // Cleanup
        file.delete();
    }

    private static void testCollectionsSorting() {
        System.out.println("\n[3] Testing Collections Framework Sorting...");
        List<Problem> sample = DemoDataGenerator.getSampleProblems();

        List<Problem> sortedByDiff = ProblemSorter.sort(sample, ProblemSorter.SortOption.DIFFICULTY_ASC);
        assertCondition("Sort Easy First", sortedByDiff.get(0).getDifficulty() == Difficulty.EASY);

        List<Problem> sortedByDiffDesc = ProblemSorter.sort(sample, ProblemSorter.SortOption.DIFFICULTY_DESC);
        assertCondition("Sort Hard First", sortedByDiffDesc.get(0).getDifficulty() == Difficulty.HARD);

        List<Problem> sortedByTime = ProblemSorter.sort(sample, ProblemSorter.SortOption.TIME_ASC);
        assertCondition("Sort Time Ascending", sortedByTime.get(0).getTimeTakenMinutes() <= sortedByTime.get(sortedByTime.size() - 1).getTimeTakenMinutes());
    }

    private static void testCollectionsFiltering() {
        System.out.println("\n[4] Testing Collections Framework Filtering & Predicates...");
        List<Problem> sample = DemoDataGenerator.getSampleProblems();

        List<Problem> easyOnly = ProblemFilter.filterByDifficulty(sample, Difficulty.EASY);
        assertCondition("Filter Difficulty Easy Count", easyOnly.stream().allMatch(p -> p.getDifficulty() == Difficulty.EASY));

        List<Problem> dpOnly = ProblemFilter.filterByTopic(sample, Topic.DYNAMIC_PROGRAMMING);
        assertCondition("Filter Topic DP", dpOnly.stream().allMatch(p -> p.getTopic() == Topic.DYNAMIC_PROGRAMMING));

        List<Problem> tagFiltered = ProblemFilter.filter(sample, new ProblemFilter.Criteria().setTag("sliding-window"));
        assertCondition("Filter Tag sliding-window", !tagFiltered.isEmpty());
    }

    private static void testStatisticsCalculations() {
        System.out.println("\n[5] Testing Statistics Module Aggregations...");
        List<Problem> sample = DemoDataGenerator.getSampleProblems();
        StatisticsService statsService = new StatisticsService();
        StatisticsService.SummaryReport report = statsService.generateReport(sample);

        assertCondition("Total Problems Count", report.getTotalProblems() == sample.size());
        assertCondition("Solved Percentage > 0", report.getSolvedPercentage() > 0.0);
        assertCondition("Average Time Spent Calculated", report.getAverageTimeMinutes() > 0.0);
        assertCondition("Difficulty Breakdown Contains EASY", report.getDifficultyCounts().containsKey(Difficulty.EASY));
    }

    private static void testSearchAndKeywordMatching() {
        System.out.println("\n[6] Testing Search & Keyword Matching...");
        List<Problem> sample = DemoDataGenerator.getSampleProblems();

        List<Problem> results = ProblemFilter.searchByKeyword(sample, "Islands");
        assertCondition("Search Title Keyword", results.size() == 1 && results.get(0).getTitle().contains("Islands"));

        List<Problem> noteResults = ProblemFilter.searchByKeyword(sample, "Patience Sorting");
        assertCondition("Search Notes Keyword", noteResults.size() == 1 && noteResults.get(0).getTitle().contains("Longest Increasing Subsequence"));
    }
}
