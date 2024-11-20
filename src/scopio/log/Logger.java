package scopio.log;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.util.converter.LocalDateTimeStringConverter;
import scopio.file.FileProcessor;

public class Logger extends FileProcessor {
    public Logger() {
        super(new File("../../log/.log"));
    }

    public void writeNewLogEntry(String content, LogLevel logLevel) {
        appendWrite("["+LocalDateTime.now()+"]: "+content+" :"+logLevel.getLog()+"\n");
    }

    public void clearLog() {
        write("");
    }

    public List<LogEntry> readLogEntries() {
        List<LogEntry> logEntries = new ArrayList<>();
        String raw = read();
        String[] split = raw.split("\n");
        for(String logEntry : split) {
            LogLevel logLevel = LogLevel.getLogLevelFromString(logEntry.substring(logEntry.lastIndexOf(':')+1));
            String timestamp = logEntry.substring(1, logEntry.indexOf(']'));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.n");
            LocalDateTime localDateTime = new LocalDateTimeStringConverter(formatter, null).fromString(timestamp);
            String content = logEntry.substring(logEntry.indexOf(';')+2, logEntry.lastIndexOf(':')-1);
            logEntries.add(new LogEntry(logLevel, content, localDateTime));
        }
        return logEntries;
    }
}
