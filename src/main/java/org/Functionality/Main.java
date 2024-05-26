package org.Functionality;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LogAnalyzer analyzer = new LogAnalyzer();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the URL to download logs:");
        String url = scanner.nextLine();

        try {
            analyzer.loadLogsFromURL(url);

            System.out.println("=== Web Server Log Analysis ===");
            System.out.println("Total number of requests: " + analyzer.getRequestCount());
            System.out.println("\n=== Response Code Distribution ===");
            analyzer.getResponseCodeDistribution().forEach((code, count) ->
                    System.out.println("Response Code: " + code + " - Count: " + count)
            );

            System.out.println("\n=== Top Requested URLs ===");
            List<String> topUrls = analyzer.getTopRequestedURLs(5);  // For example, top 5 URLs
            for (int i = 0; i < topUrls.size(); i++) {
                System.out.println((i + 1) + ". " + topUrls.get(i));
            }

            System.out.println("\n=== Requests from IP 192.168.0.1 ===");
            List<LogEntry> requestsByIP = analyzer.getRequestsByIP("192.168.0.1");
            for (LogEntry entry : requestsByIP) {
                System.out.println(entry);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
