package com.dsatracker.filter;

import com.dsatracker.model.Difficulty;
import com.dsatracker.model.Problem;
import com.dsatracker.model.Status;
import com.dsatracker.model.Topic;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProblemFilter {

    public static class Criteria {
        private Topic topic;
        private Difficulty difficulty;
        private Status status;
        private String tag;
        private String platform;
        private String searchKeyword;

        public Criteria setTopic(Topic topic) {
            this.topic = topic;
            return this;
        }

        public Criteria setDifficulty(Difficulty difficulty) {
            this.difficulty = difficulty;
            return this;
        }

        public Criteria setStatus(Status status) {
            this.status = status;
            return this;
        }

        public Criteria setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public Criteria setPlatform(String platform) {
            this.platform = platform;
            return this;
        }

        public Criteria setSearchKeyword(String keyword) {
            this.searchKeyword = keyword;
            return this;
        }

        public Topic getTopic() { return topic; }
        public Difficulty getDifficulty() { return difficulty; }
        public Status getStatus() { return status; }
        public String getTag() { return tag; }
        public String getPlatform() { return platform; }
        public String getSearchKeyword() { return searchKeyword; }
    }

    public static List<Problem> filter(List<Problem> problems, Criteria criteria) {
        if (problems == null || criteria == null) return problems;

        Predicate<Problem> predicate = p -> true;

        if (criteria.getTopic() != null) {
            predicate = predicate.and(p -> p.getTopic() == criteria.getTopic());
        }

        if (criteria.getDifficulty() != null) {
            predicate = predicate.and(p -> p.getDifficulty() == criteria.getDifficulty());
        }

        if (criteria.getStatus() != null) {
            predicate = predicate.and(p -> p.getStatus() == criteria.getStatus());
        }

        if (criteria.getTag() != null && !criteria.getTag().trim().isEmpty()) {
            String cleanTag = criteria.getTag().trim().toLowerCase();
            predicate = predicate.and(p -> p.getTags().stream().anyMatch(t -> t.toLowerCase().contains(cleanTag)));
        }

        if (criteria.getPlatform() != null && !criteria.getPlatform().trim().isEmpty()) {
            String cleanPlatform = criteria.getPlatform().trim().toLowerCase();
            predicate = predicate.and(p -> p.getPlatform().toLowerCase().contains(cleanPlatform));
        }

        if (criteria.getSearchKeyword() != null && !criteria.getSearchKeyword().trim().isEmpty()) {
            String kw = criteria.getSearchKeyword().trim().toLowerCase();
            predicate = predicate.and(p -> p.getTitle().toLowerCase().contains(kw) ||
                    p.getNotes().toLowerCase().contains(kw));
        }

        return problems.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public static List<Problem> filterByTopic(List<Problem> problems, Topic topic) {
        return filter(problems, new Criteria().setTopic(topic));
    }

    public static List<Problem> filterByDifficulty(List<Problem> problems, Difficulty difficulty) {
        return filter(problems, new Criteria().setDifficulty(difficulty));
    }

    public static List<Problem> filterByStatus(List<Problem> problems, Status status) {
        return filter(problems, new Criteria().setStatus(status));
    }

    public static List<Problem> searchByKeyword(List<Problem> problems, String keyword) {
        return filter(problems, new Criteria().setSearchKeyword(keyword));
    }
}
