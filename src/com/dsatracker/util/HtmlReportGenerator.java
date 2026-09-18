package com.dsatracker.util;

import com.dsatracker.model.Difficulty;
import com.dsatracker.model.Problem;
import com.dsatracker.model.Topic;
import com.dsatracker.service.StatisticsService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Desktop;
import java.util.List;
import java.util.Map;

public class HtmlReportGenerator {

    public static boolean generateHtmlReport(List<Problem> problems, StatisticsService.SummaryReport report, String filePath) {
        if (problems == null || report == null || filePath == null) return false;

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html lang=\"en\">\n");
        html.append("<head>\n");
        html.append("  <meta charset=\"UTF-8\">\n");
        html.append("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        html.append("  <title>DSA Problem Tracker - Web Analytics Dashboard</title>\n");
        html.append("  <style>\n");
        html.append("    :root { --bg: #0f172a; --card: #1e293b; --accent: #38bdf8; --text: #f8fafc; --easy: #22c55e; --medium: #eab308; --hard: #ef4444; }\n");
        html.append("    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: var(--bg); color: var(--text); margin: 0; padding: 30px; }\n");
        html.append("    .container { max-width: 1200px; margin: 0 auto; }\n");
        html.append("    header { text-align: center; padding-bottom: 30px; border-bottom: 1px solid #334155; margin-bottom: 30px; }\n");
        html.append("    h1 { margin: 0; font-size: 2.2rem; color: var(--accent); }\n");
        html.append("    .subtitle { color: #94a3b8; font-size: 1rem; margin-top: 5px; }\n");
        html.append("    .stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(220px, 1fr)); gap: 20px; margin-bottom: 30px; }\n");
        html.append("    .card { background: var(--card); padding: 20px; border-radius: 12px; border: 1px solid #334155; box-shadow: 0 4px 12px rgba(0,0,0,0.3); }\n");
        html.append("    .card-title { font-size: 0.85rem; text-transform: uppercase; color: #94a3b8; letter-spacing: 1px; }\n");
        html.append("    .card-value { font-size: 2.2rem; font-weight: bold; margin-top: 10px; color: #fff; }\n");
        html.append("    .progress-bar-bg { background: #334155; height: 12px; border-radius: 6px; overflow: hidden; margin-top: 15px; }\n");
        html.append("    .progress-bar-fill { background: linear-gradient(90deg, #38bdf8, #818cf8); height: 100%; transition: width 0.5s ease; }\n");
        html.append("    .section-title { font-size: 1.4rem; margin-top: 40px; margin-bottom: 20px; color: var(--accent); }\n");
        html.append("    table { width: 100%; border-collapse: collapse; background: var(--card); border-radius: 12px; overflow: hidden; border: 1px solid #334155; }\n");
        html.append("    th, td { padding: 14px 18px; text-align: left; border-bottom: 1px solid #334155; }\n");
        html.append("    th { background: #0f172a; color: #94a3b8; font-weight: 600; text-transform: uppercase; font-size: 0.8rem; }\n");
        html.append("    tr:hover { background: #26334d; }\n");
        html.append("    .badge { padding: 4px 10px; border-radius: 20px; font-weight: bold; font-size: 0.75rem; text-transform: uppercase; display: inline-block; }\n");
        html.append("    .badge-easy { background: rgba(34, 197, 94, 0.2); color: var(--easy); border: 1px solid var(--easy); }\n");
        html.append("    .badge-medium { background: rgba(234, 179, 8, 0.2); color: var(--medium); border: 1px solid var(--medium); }\n");
        html.append("    .badge-hard { background: rgba(239, 68, 68, 0.2); color: var(--hard); border: 1px solid var(--hard); }\n");
        html.append("    footer { text-align: center; margin-top: 50px; color: #64748b; font-size: 0.9rem; }\n");
        html.append("  </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("  <div class=\"container\">\n");
        html.append("    <header>\n");
        html.append("      <h1>🚀 DSA Problem Tracker Dashboard</h1>\n");
        html.append("      <div class=\"subtitle\">Interactive Web Performance & Problem Progress Report</div>\n");
        html.append("    </header>\n");

        // Stat Cards Grid
        html.append("    <div class=\"stats-grid\">\n");
        html.append("      <div class=\"card\">\n");
        html.append("        <div class=\"card-title\">Total Problems</div>\n");
        html.append("        <div class=\"card-value\">").append(report.getTotalProblems()).append("</div>\n");
        html.append("      </div>\n");
        html.append("      <div class=\"card\">\n");
        html.append("        <div class=\"card-title\">Solved</div>\n");
        html.append("        <div class=\"card-value\" style=\"color: var(--easy);\">").append(report.getSolvedCount()).append("</div>\n");
        html.append("      </div>\n");
        html.append("      <div class=\"card\">\n");
        html.append("        <div class=\"card-title\">Completion Rate</div>\n");
        html.append("        <div class=\"card-value\">").append(String.format("%.1f%%", report.getSolvedPercentage())).append("</div>\n");
        html.append("        <div class=\"progress-bar-bg\"><div class=\"progress-bar-fill\" style=\"width: ").append(report.getSolvedPercentage()).append("%;\"></div></div>\n");
        html.append("      </div>\n");
        html.append("      <div class=\"card\">\n");
        html.append("        <div class=\"card-title\">Avg Time / Problem</div>\n");
        html.append("        <div class=\"card-value\">").append(String.format("%.1f m", report.getAverageTimeMinutes())).append("</div>\n");
        html.append("      </div>\n");
        html.append("    </div>\n");

        // Problem Table
        html.append("    <div class=\"section-title\">📋 Solved Coding Problems List</div>\n");
        html.append("    <table>\n");
        html.append("      <thead>\n");
        html.append("        <tr><th>ID</th><th>Title</th><th>Topic</th><th>Difficulty</th><th>Status</th><th>Platform</th><th>Time</th><th>Date</th></tr>\n");
        html.append("      </thead>\n");
        html.append("      <tbody>\n");

        for (Problem p : problems) {
            String badgeClass = p.getDifficulty() == Difficulty.EASY ? "badge-easy" :
                    p.getDifficulty() == Difficulty.MEDIUM ? "badge-medium" : "badge-hard";

            html.append("        <tr>\n");
            html.append("          <td>#").append(p.getId()).append("</td>\n");
            html.append("          <td><strong>").append(escapeHtml(p.getTitle())).append("</strong></td>\n");
            html.append("          <td>").append(escapeHtml(p.getTopic().getDisplayName())).append("</td>\n");
            html.append("          <td><span class=\"badge ").append(badgeClass).append("\">").append(p.getDifficulty().getDisplayName()).append("</span></td>\n");
            html.append("          <td>").append(escapeHtml(p.getStatus().getDisplayName())).append("</td>\n");
            html.append("          <td>").append(escapeHtml(p.getPlatform())).append("</td>\n");
            html.append("          <td>").append(p.getTimeTakenMinutes()).append(" mins</td>\n");
            html.append("          <td>").append(p.getDateSolved() != null ? p.getDateSolved().toString() : "N/A").append("</td>\n");
            html.append("        </tr>\n");
        }

        html.append("      </tbody>\n");
        html.append("    </table>\n");

        html.append("    <footer>DSA Problem Tracker | Java & Collections Framework</footer>\n");
        html.append("  </div>\n");
        html.append("</body>\n");
        html.append("</html>\n");

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(html.toString());
            return true;
        } catch (IOException e) {
            System.err.println("Error generating HTML dashboard: " + e.getMessage());
            return false;
        }
    }

    public static void openInBrowser(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists() && Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(file.toURI());
            }
        } catch (Exception e) {
            System.out.println("Generated report at: " + new File(filePath).getAbsolutePath());
        }
    }

    private static String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}
