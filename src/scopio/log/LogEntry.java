package scopio.log;
import java.time.LocalDateTime;

public class LogEntry {
    private LogLevel logLevel;
    private String content;
    private LocalDateTime timestamp;

    public LogEntry(LogLevel logLevel, String content, LocalDateTime timestamp) {
        this.logLevel = logLevel;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}