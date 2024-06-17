package logging;

import launcher.Main;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class LogHandler extends Handler {

    public LogHandler() {
        this.setFormatter(new LogFormatter());
    }


    @Override
    public void publish(LogRecord record) {
        Main.logQueue.add(getFormatter().format(record));
    }

    @Override
    public void flush() {}

    @Override
    public void close() throws SecurityException {
        //
    }
}
