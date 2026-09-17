# 🚀 DSA Problem Tracker (Java & Collections Framework)

A console-based application built in **Java** leveraging the **Java Collections Framework** and **Modular Object-Oriented Architecture** to track, filter, sort, and analyze solved Data Structures & Algorithms (DSA) problems.

---

## ✨ Features & Resume Bullet Points

- **Console-based Application**: Efficiently track and manage solved coding problems across platforms (LeetCode, HackerRank, GeeksforGeeks, Codeforces).
- **Java Collections Framework Integration**:
  - `List<Problem>` (`ArrayList`) for dynamic data storage.
  - `Map<Topic, Long>`, `Map<Difficulty, Long>`, `DoubleSummaryStatistics` for multi-dimensional performance analytics.
  - Custom `Comparator<Problem>` implementations with `Collections.sort()` for dynamic sorting.
  - Functional `Predicate<Problem>` & `Stream` API pipelines for optimized multi-criteria filtering and keyword search.
- **Modular OOP-based Architecture**: Clean separation into domain models (`Problem`, `Topic`, `Difficulty`, `Status`), repository abstraction (`ProblemRepository`), service layers (`ProblemService`, `StatisticsService`), and presentation layer (`ConsoleUI`, `TableFormatter`).
- **Statistics & Performance Analytics Module**: Analyze topic-wise distribution, difficulty breakdown (Easy/Medium/Hard), completion percentages, average time spent per topic/difficulty, and top practiced topics.
- **Persistent Storage**: Data automatically saved to `data/problems.json` using custom serialization without third-party dependencies.
- **Structured Interactive UI**: Clean ASCII banners, formatted tabular views, colorized difficulty levels, interactive menu options, and sample demo data generation.

---

## 🛠 Project Structure

```
DSA Problem Tracker/
├── src/
│   └── com/
│       └── dsatracker/
│           ├── Main.java                        # App entry point
│           ├── model/                           # Domain entities & Enums
│           │   ├── Problem.java
│           │   ├── Difficulty.java
│           │   ├── Status.java
│           │   └── Topic.java
│           ├── repository/                      # Data Access Layer
│           │   ├── ProblemRepository.java
│           │   └── FileProblemRepository.java
│           ├── service/                         # Business & Analytics Logic
│           │   ├── ProblemService.java
│           │   └── StatisticsService.java
│           ├── filter/                          # Filtering Engine
│           │   └── ProblemFilter.java
│           ├── sort/                            # Sorting Engine
│           │   └── ProblemSorter.java
│           ├── ui/                              # Console Presentation Layer
│           │   ├── ConsoleUI.java
│           │   └── TableFormatter.java
│           └── util/                            # Utilities & Demo Data
│               ├── DemoDataGenerator.java
│               └── SimpleJsonUtil.java
├── tests/
│   └── com/
│       └── dsatracker/
│           └── TestRunner.java                  # Standalone Automated Test Suite
├── data/
│   └── problems.json                            # JSON Persistence file
├── build.bat                                    # Windows compilation script
├── run.bat                                      # Windows execution script
└── README.md
```

---

## ⚡ Quick Start & Compilation Guide

### Requirements
- Java Development Kit (JDK) 11 or higher installed (`javac` and `java` in System PATH).

### 1. Build the Project
Run `build.bat` on Windows or execute:
```cmd
mkdir bin
javac -d bin -sourcepath src src/com/dsatracker/model/*.java src/com/dsatracker/repository/*.java src/com/dsatracker/sort/*.java src/com/dsatracker/filter/*.java src/com/dsatracker/service/*.java src/com/dsatracker/ui/*.java src/com/dsatracker/util/*.java src/com/dsatracker/Main.java tests/com/dsatracker/TestRunner.java
```

### 2. Run Automated Test Suite
```cmd
java -cp bin com.dsatracker.TestRunner
```

### 3. Run the Application
Run `run.bat` on Windows or execute:
```cmd
java -cp bin com.dsatracker.Main
```

---

## 📊 Analytics Dashboard Preview

```
=========================================================================================
                       📊 PERFORMANCE & TOPIC-WISE ANALYTICS REPORT                       
=========================================================================================
  Total Problems Recorded : 10
  ✔ Solved                : 8
  ⏳ Attempted             : 1
  🔄 Needs Revisit        : 1
  Progress Rate           : 80.0%
  Progress: [========================>     ] 80.0%

  Average Time / Problem  : 22.1 mins
  Top Practiced Topic     : Dynamic Programming

-- 🎯 Difficulty Breakdown --
  Easy     :  3 problem(s) ( 30.0%)
  Medium   :  5 problem(s) ( 50.0%)
  Hard     :  2 problem(s) ( 20.0%)

-- 📚 Topic Breakdown & Time Spent --
  Arrays & Vectors               :  1 problem(s) | Avg Time: 12.0 mins
  Two Pointers & Sliding Window  :  2 problem(s) | Avg Time: 30.0 mins
  Linked Lists                   :  1 problem(s) | Avg Time: 10.0 mins
  Strings                        :  1 problem(s) | Avg Time: 22.0 mins
  Graphs & BFS/DFS               :  1 problem(s) | Avg Time: 30.0 mins
  Dynamic Programming            :  2 problem(s) | Avg Time: 26.5 mins
  Heap & Priority Queue          :  1 problem(s) | Avg Time: 40.0 mins
  Trees & Binary Search Trees    :  1 problem(s) | Avg Time: 15.0 mins
=========================================================================================
```
