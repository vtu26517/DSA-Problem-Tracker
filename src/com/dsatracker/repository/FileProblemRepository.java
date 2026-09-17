package com.dsatracker.repository;

import com.dsatracker.model.Problem;
import com.dsatracker.util.SimpleJsonUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileProblemRepository implements ProblemRepository {
    private final String filePath;
    private final List<Problem> cache;

    public FileProblemRepository(String filePath) {
        this.filePath = filePath;
        this.cache = new ArrayList<>();
        loadFromFile();
    }

    private synchronized void loadFromFile() {
        cache.clear();
        File file = new File(filePath);
        if (file.exists()) {
            try {
                String content = new String(Files.readAllBytes(Paths.get(filePath)));
                List<Problem> loaded = SimpleJsonUtil.parseJson(content);
                cache.addAll(loaded);
            } catch (IOException e) {
                System.err.println("Error reading data file: " + e.getMessage());
            }
        }
    }

    private synchronized void syncToFile() {
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            String json = SimpleJsonUtil.toJson(cache);
            Files.write(Paths.get(filePath), json.getBytes());
        } catch (IOException e) {
            System.err.println("Error persisting data file: " + e.getMessage());
        }
    }

    @Override
    public synchronized Problem save(Problem problem) {
        if (problem.getId() <= 0) {
            problem.setId(generateNextId());
            cache.add(problem);
        } else {
            Optional<Problem> existing = findById(problem.getId());
            if (existing.isPresent()) {
                int index = cache.indexOf(existing.get());
                cache.set(index, problem);
            } else {
                cache.add(problem);
            }
        }
        syncToFile();
        return problem;
    }

    @Override
    public synchronized Optional<Problem> findById(int id) {
        return cache.stream().filter(p -> p.getId() == id).findFirst();
    }

    @Override
    public synchronized List<Problem> findAll() {
        return new ArrayList<>(cache);
    }

    @Override
    public synchronized boolean deleteById(int id) {
        boolean removed = cache.removeIf(p -> p.getId() == id);
        if (removed) {
            syncToFile();
        }
        return removed;
    }

    @Override
    public synchronized void saveAll(List<Problem> problems) {
        for (Problem p : problems) {
            if (p.getId() <= 0) {
                p.setId(generateNextId());
            }
            // replace or add
            Optional<Problem> existing = findById(p.getId());
            if (existing.isPresent()) {
                int index = cache.indexOf(existing.get());
                cache.set(index, p);
            } else {
                cache.add(p);
            }
        }
        syncToFile();
    }

    @Override
    public synchronized int generateNextId() {
        return cache.stream().mapToInt(Problem::getId).max().orElse(0) + 1;
    }

    @Override
    public synchronized void clearAll() {
        cache.clear();
        syncToFile();
    }
}
