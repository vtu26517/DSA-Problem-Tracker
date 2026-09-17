package com.dsatracker.service;

import com.dsatracker.model.Difficulty;
import com.dsatracker.model.Problem;
import com.dsatracker.model.Status;
import com.dsatracker.model.Topic;

import java.util.*;
import java.util.stream.Collectors;

public class StatisticsService {

    public static class SummaryReport {
        private final int totalProblems;
        private final int solvedCount;
        private final int attemptedCount;
        private final int revisitCount;
        private final double solvedPercentage;
        private final double averageTimeMinutes;
        private final Map<Difficulty, Long> difficultyCounts;
        private final Map<Topic, Long> topicCounts;
        private final Map<Topic, Double> topicAverageTime;
        private final Map<String, Long> platformCounts;
        private final Topic mostPracticedTopic;

        public SummaryReport(int totalProblems, int solvedCount, int attemptedCount, int revisitCount,
                             double solvedPercentage, double averageTimeMinutes,
                             Map<Difficulty, Long> difficultyCounts, Map<Topic, Long> topicCounts,
                             Map<Topic, Double> topicAverageTime, Map<String, Long> platformCounts,
                             Topic mostPracticedTopic) {
            this.totalProblems = totalProblems;
            this.solvedCount = solvedCount;
            this.attemptedCount = attemptedCount;
            this.revisitCount = revisitCount;
            this.solvedPercentage = solvedPercentage;
            this.averageTimeMinutes = averageTimeMinutes;
            this.difficultyCounts = difficultyCounts;
            this.topicCounts = topicCounts;
            this.topicAverageTime = topicAverageTime;
            this.platformCounts = platformCounts;
            this.mostPracticedTopic = mostPracticedTopic;
        }

        public int getTotalProblems() { return totalProblems; }
        public int getSolvedCount() { return solvedCount; }
        public int getAttemptedCount() { return attemptedCount; }
        public int getRevisitCount() { return revisitCount; }
        public double getSolvedPercentage() { return solvedPercentage; }
        public double getAverageTimeMinutes() { return averageTimeMinutes; }
        public Map<Difficulty, Long> getDifficultyCounts() { return difficultyCounts; }
        public Map<Topic, Long> getTopicCounts() { return topicCounts; }
        public Map<Topic, Double> getTopicAverageTime() { return topicAverageTime; }
        public Map<String, Long> getPlatformCounts() { return platformCounts; }
        public Topic getMostPracticedTopic() { return mostPracticedTopic; }
    }

    public SummaryReport generateReport(List<Problem> problems) {
        if (problems == null || problems.isEmpty()) {
            return new SummaryReport(0, 0, 0, 0, 0.0, 0.0,
                    EnumSet.allOf(Difficulty.class).stream().collect(Collectors.toMap(d -> d, d -> 0L)),
                    new EnumMap<>(Topic.class), new EnumMap<>(Topic.class), new HashMap<>(), null);
        }

        int total = problems.size();

        Map<Status, Long> statusCounts = problems.stream()
                .collect(Collectors.groupingBy(Problem::getStatus, Collectors.counting()));

        int solved = statusCounts.getOrDefault(Status.SOLVED, 0L).intValue();
        int attempted = statusCounts.getOrDefault(Status.ATTEMPTED, 0L).intValue();
        int revisit = statusCounts.getOrDefault(Status.REVISIT, 0L).intValue();

        double solvedPct = total > 0 ? (solved * 100.0) / total : 0.0;

        double avgTime = problems.stream()
                .mapToInt(Problem::getTimeTakenMinutes)
                .average()
                .orElse(0.0);

        Map<Difficulty, Long> diffCounts = problems.stream()
                .collect(Collectors.groupingBy(Problem::getDifficulty, Collectors.counting()));

        // Ensure all difficulties are present in map
        for (Difficulty d : Difficulty.values()) {
            diffCounts.putIfAbsent(d, 0L);
        }

        Map<Topic, Long> topicCounts = problems.stream()
                .collect(Collectors.groupingBy(Problem::getTopic, Collectors.counting()));

        Map<Topic, Double> topicAvgTime = problems.stream()
                .collect(Collectors.groupingBy(Problem::getTopic,
                        Collectors.averagingInt(Problem::getTimeTakenMinutes)));

        Map<String, Long> platformCounts = problems.stream()
                .collect(Collectors.groupingBy(Problem::getPlatform, Collectors.counting()));

        Topic mostPracticed = topicCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        return new SummaryReport(total, solved, attempted, revisit, solvedPct, avgTime,
                diffCounts, topicCounts, topicAvgTime, platformCounts, mostPracticed);
    }
}
