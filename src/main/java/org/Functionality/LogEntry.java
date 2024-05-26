package org.Functionality;
import java.time.LocalDateTime;

public class LogEntry {
    private String ipAddress;
    private LocalDateTime timestamp;
    private String requestMethod;
    private String url;
    private int responseCode;
    private int responseSize;

    public LogEntry(String ipAddress, LocalDateTime timestamp, String requestMethod, String url, int responseCode, int responseSize) {
        this.ipAddress = ipAddress;
        this.timestamp = timestamp;
        this.requestMethod = requestMethod;
        this.url = url;
        this.responseCode = responseCode;
        this.responseSize = responseSize;
    }

    public String getIpAddress() { return ipAddress; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getRequestMethod() { return requestMethod; }
    public String getUrl() { return url; }
    public int getResponseCode() { return responseCode; }
    public int getResponseSize() { return responseSize; }

    @Override
    public String toString() {
        return ipAddress + " - " + timestamp + " - " + requestMethod + " - " + url + " - " + responseCode + " - " + responseSize;
    }
}
