package com.dsatracker;

import com.dsatracker.repository.FileProblemRepository;
import com.dsatracker.repository.ProblemRepository;
import com.dsatracker.service.ProblemService;
import com.dsatracker.ui.ConsoleUI;
import com.dsatracker.util.DemoDataGenerator;

public class Main {
    public static void main(String[] args) {
        String dataFilePath = "data/problems.json";
        ProblemRepository repository = new FileProblemRepository(dataFilePath);
        ProblemService problemService = new ProblemService(repository);

        // Pre-populate sample demo problems if repository is initially empty
        if (problemService.getAllProblems().isEmpty()) {
            problemService.loadDemoData(DemoDataGenerator.getSampleProblems());
        }

        ConsoleUI consoleUI = new ConsoleUI(problemService);
        consoleUI.start();
    }
}
