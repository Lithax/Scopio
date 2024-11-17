package scopio.log;

import java.io.File;

import scopio.file.FileProcessor;

public class Logger extends FileProcessor {
    public Logger() {
        super(new File("../../log/.log"));
    }

    public void writeNewLogEntry(String content, LogLevel logLevel) {
        write("["+logLevel.getLog()+"]: "+content+"\n");
    }

    public void clearLog() {
        write("");
    }
}
