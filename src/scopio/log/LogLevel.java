package scopio.log;

public enum LogLevel {
    INFO("info"),
    WARNING("warning"),
    ERROR("error"),
    CRITICAL("critical");

    private final String log;

    LogLevel(String log) {
        this.log = log;
    }

    public String getLog() {
        return log;
    }
}
