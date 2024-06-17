package logging;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class LogHandler extends Handler {



    @Override
    public void publish(LogRecord record) {
        setFormatter();

        Main.logQueue.add(getFormatter().format(record));
    }

    @Override
    public void flush() {}

    @Override
    public void close() throws SecurityException {
        //
    }
}
