package com.dsatracker.repository;

import com.dsatracker.model.Problem;
import java.util.List;
import java.util.Optional;

public interface ProblemRepository {
    Problem save(Problem problem);
    Optional<Problem> findById(int id);
    List<Problem> findAll();
    boolean deleteById(int id);
    void saveAll(List<Problem> problems);
    int generateNextId();
    void clearAll();
}
