package com.dsatracker.util;

import com.dsatracker.model.Difficulty;
import com.dsatracker.model.Problem;
import com.dsatracker.model.Status;
import com.dsatracker.model.Topic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class DemoDataGenerator {

    public static List<Problem> getSampleProblems() {
        List<Problem> list = new ArrayList<>();

        list.add(new Problem(1, "Two Sum", Topic.ARRAYS, Difficulty.EASY, Status.SOLVED,
                "LeetCode", 12, "Used HashMap for O(n) time complexity lookup.",
                "https://leetcode.com/problems/two-sum/",
                new HashSet<>(Arrays.asList("hashmap", "array", "easy")),
                LocalDate.now().minusDays(14)));

        list.add(new Problem(2, "3Sum", Topic.TWO_POINTERS, Difficulty.MEDIUM, Status.SOLVED,
                "LeetCode", 25, "Sort array first, then use two pointers to avoid duplicates.",
                "https://leetcode.com/problems/3sum/",
                new HashSet<>(Arrays.asList("two-pointers", "sorting", "array")),
                LocalDate.now().minusDays(12)));

        list.add(new Problem(3, "Reverse Linked List", Topic.LINKED_LIST, Difficulty.EASY, Status.SOLVED,
                "LeetCode", 10, "Iterative pointer reassignment (prev, curr, next).",
                "https://leetcode.com/problems/reverse-linked-list/",
                new HashSet<>(Arrays.asList("linked-list", "pointers")),
                LocalDate.now().minusDays(10)));

        list.add(new Problem(4, "Longest Substring Without Repeating Characters", Topic.STRINGS, Difficulty.MEDIUM, Status.SOLVED,
                "LeetCode", 22, "Sliding window with Set/Map tracking character frequencies.",
                "https://leetcode.com/problems/longest-substring-without-repeating-characters/",
                new HashSet<>(Arrays.asList("sliding-window", "string", "hashmap")),
                LocalDate.now().minusDays(9)));

        list.add(new Problem(5, "Number of Islands", Topic.GRAPHS, Difficulty.MEDIUM, Status.SOLVED,
                "LeetCode", 30, "Grid BFS/DFS matrix traversal, marking visited nodes in-place.",
                "https://leetcode.com/problems/number-of-islands/",
                new HashSet<>(Arrays.asList("bfs", "dfs", "graph", "matrix")),
                LocalDate.now().minusDays(7)));

        list.add(new Problem(6, "Longest Increasing Subsequence", Topic.DYNAMIC_PROGRAMMING, Difficulty.MEDIUM, Status.REVISIT,
                "LeetCode", 45, "O(n^2) DP solution achieved. Need to optimize to O(n log n) via Patience Sorting.",
                "https://leetcode.com/problems/longest-increasing-subsequence/",
                new HashSet<>(Arrays.asList("dp", "binary-search")),
                LocalDate.now().minusDays(5)));

        list.add(new Problem(7, "Merge K Sorted Lists", Topic.HEAP_PRIORITY_QUEUE, Difficulty.HARD, Status.ATTEMPTED,
                "LeetCode", 40, "Attempted using Min-Heap. Got TLE on extreme testcases.",
                "https://leetcode.com/problems/merge-k-sorted-lists/",
                new HashSet<>(Arrays.asList("heap", "priority-queue", "divide-and-conquer")),
                LocalDate.now().minusDays(4)));

        list.add(new Problem(8, "Trapping Rain Water", Topic.TWO_POINTERS, Difficulty.HARD, Status.SOLVED,
                "LeetCode", 35, "Used 2-pointer approach maintain maxLeft and maxRight.",
                "https://leetcode.com/problems/trapping-rain-water/",
                new HashSet<>(Arrays.asList("two-pointers", "stack")),
                LocalDate.now().minusDays(3)));

        list.add(new Problem(9, "Binary Tree Level Order Traversal", Topic.TREES, Difficulty.MEDIUM, Status.SOLVED,
                "GeeksforGeeks", 15, "Standard BFS queue traversal recording level sizes.",
                "https://practice.geeksforgeeks.org/problems/level-order-traversal/",
                new HashSet<>(Arrays.asList("tree", "bfs", "queue")),
                LocalDate.now().minusDays(2)));

        list.add(new Problem(10, "Climbing Stairs", Topic.DYNAMIC_PROGRAMMING, Difficulty.EASY, Status.SOLVED,
                "LeetCode", 8, "Fibonacci relationship - stored last 2 states in O(1) space.",
                "https://leetcode.com/problems/climbing-stairs/",
                new HashSet<>(Arrays.asList("dp", "math")),
                LocalDate.now().minusDays(1)));

        return list;
    }
}
