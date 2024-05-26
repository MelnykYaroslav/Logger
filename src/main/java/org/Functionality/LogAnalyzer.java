package org.Functionality;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class LogAnalyzer {
    private List<LogEntry> logEntries = new ArrayList<>();

    public void loadLogsFromFile(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        logEntries = lines.parallelStream()
                .map(this::parseLogEntry)
                .collect(Collectors.toList());
    }

    public void loadLogsFromURL(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            logEntries = reader.lines()
                    .parallel()
                    .map(this::parseLogEntry)
                    .collect(Collectors.toList());
        }
    }

    private LogEntry parseLogEntry(String line) {
        String[] parts = line.split(" ");
        String ipAddress = parts[0];
        LocalDateTime timestamp = LocalDateTime.parse(parts[3], DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss"));
        String requestMethod = parts[5].substring(1);
        String url = parts[6];
        int responseCode = Integer.parseInt(parts[8]);
        int responseSize = Integer.parseInt(parts[9]);

        return new LogEntry(ipAddress, timestamp, requestMethod, url, responseCode, responseSize);
    }

    public long getRequestCount() {
        return logEntries.size();
    }

    public Map<String, Long> getResponseCodeDistribution() {
        return logEntries.stream()
                .collect(Collectors.groupingBy(entry -> convertResponseCodeToText(entry.getResponseCode()), Collectors.counting()));
    }

    private String convertResponseCodeToText(int code) {
        switch (code) {
            case 200: return "OK";
            case 302: return "Found";
            case 404: return "Not Found";
            default: return "Other";
        }
    }

    public List<String> getTopRequestedURLs(int n) {
        return logEntries.stream()
                .collect(Collectors.groupingBy(LogEntry::getUrl, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
