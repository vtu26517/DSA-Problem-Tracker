package com.dsatracker.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Problem {
    private int id;
    private String title;
    private Topic topic;
    private Difficulty difficulty;
    private Status status;
    private String platform; // LeetCode, HackerRank, Codeforces, GeeksforGeeks, etc.
    private int timeTakenMinutes;
    private String notes;
    private String solutionUrl;
    private Set<String> tags;
    private LocalDate dateSolved;

    public Problem() {
        this.tags = new HashSet<>();
        this.dateSolved = LocalDate.now();
        this.status = Status.SOLVED;
        this.difficulty = Difficulty.EASY;
        this.topic = Topic.ARRAYS;
    }

    public Problem(int id, String title, Topic topic, Difficulty difficulty, Status status,
                   String platform, int timeTakenMinutes, String notes, String solutionUrl,
                   Set<String> tags, LocalDate dateSolved) {
        this.id = id;
        this.title = title;
        this.topic = topic;
        this.difficulty = difficulty;
        this.status = status;
        this.platform = platform != null ? platform : "LeetCode";
        this.timeTakenMinutes = timeTakenMinutes;
        this.notes = notes != null ? notes : "";
        this.solutionUrl = solutionUrl != null ? solutionUrl : "";
        this.tags = tags != null ? new HashSet<>(tags) : new HashSet<>();
        this.dateSolved = dateSolved != null ? dateSolved : LocalDate.now();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getTimeTakenMinutes() {
        return timeTakenMinutes;
    }

    public void setTimeTakenMinutes(int timeTakenMinutes) {
        this.timeTakenMinutes = timeTakenMinutes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getSolutionUrl() {
        return solutionUrl;
    }

    public void setSolutionUrl(String solutionUrl) {
        this.solutionUrl = solutionUrl;
    }

    public Set<String> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public void setTags(Set<String> tags) {
        this.tags = tags != null ? new HashSet<>(tags) : new HashSet<>();
    }

    public void addTag(String tag) {
        if (tag != null && !tag.trim().isEmpty()) {
            this.tags.add(tag.trim().toLowerCase());
        }
    }

    public void removeTag(String tag) {
        if (tag != null) {
            this.tags.remove(tag.trim().toLowerCase());
        }
    }

    public LocalDate getDateSolved() {
        return dateSolved;
    }

    public void setDateSolved(LocalDate dateSolved) {
        this.dateSolved = dateSolved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Problem problem = (Problem) o;
        return id == problem.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s | %s) - %s [%s]",
                id, title, topic.getDisplayName(), difficulty.getDisplayName(),
                status.getDisplayName(), platform);
    }
}
