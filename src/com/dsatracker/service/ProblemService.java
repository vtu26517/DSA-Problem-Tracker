package com.dsatracker.service;

import com.dsatracker.filter.ProblemFilter;
import com.dsatracker.model.Difficulty;
import com.dsatracker.model.Problem;
import com.dsatracker.model.Status;
import com.dsatracker.model.Topic;
import com.dsatracker.repository.ProblemRepository;
import com.dsatracker.sort.ProblemSorter;

import java.util.List;
import java.util.Optional;

public class ProblemService {
    private final ProblemRepository repository;
    private final StatisticsService statisticsService;

    public ProblemService(ProblemRepository repository) {
        this.repository = repository;
        this.statisticsService = new StatisticsService();
    }

    public Problem addProblem(Problem problem) {
        validateProblem(problem);
        return repository.save(problem);
    }

    public Problem updateProblem(Problem problem) {
        if (problem == null || problem.getId() <= 0) {
            throw new IllegalArgumentException("Invalid problem ID for update.");
        }
        validateProblem(problem);
        return repository.save(problem);
    }

    public boolean deleteProblem(int id) {
        return repository.deleteById(id);
    }

    public Optional<Problem> getProblemById(int id) {
        return repository.findById(id);
    }

    public List<Problem> getAllProblems() {
        return repository.findAll();
    }

    public List<Problem> getSortedProblems(ProblemSorter.SortOption option) {
        return ProblemSorter.sort(repository.findAll(), option);
    }

    public List<Problem> getFilteredProblems(ProblemFilter.Criteria criteria) {
        return ProblemFilter.filter(repository.findAll(), criteria);
    }

    public List<Problem> getProblemsByTopic(Topic topic) {
        return ProblemFilter.filterByTopic(repository.findAll(), topic);
    }

    public List<Problem> getProblemsByDifficulty(Difficulty difficulty) {
        return ProblemFilter.filterByDifficulty(repository.findAll(), difficulty);
    }

    public List<Problem> getProblemsByStatus(Status status) {
        return ProblemFilter.filterByStatus(repository.findAll(), status);
    }

    public List<Problem> searchProblems(String keyword) {
        return ProblemFilter.searchByKeyword(repository.findAll(), keyword);
    }

    public StatisticsService.SummaryReport getStatistics() {
        return statisticsService.generateReport(repository.findAll());
    }

    public void loadDemoData(List<Problem> demoProblems) {
        repository.saveAll(demoProblems);
    }

    public void clearAllProblems() {
        repository.clearAll();
    }

    private void validateProblem(Problem problem) {
        if (problem == null) {
            throw new IllegalArgumentException("Problem cannot be null.");
        }
        if (problem.getTitle() == null || problem.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Problem title cannot be empty.");
        }
        if (problem.getTimeTakenMinutes() < 0) {
            throw new IllegalArgumentException("Time taken cannot be negative.");
        }
    }
}
