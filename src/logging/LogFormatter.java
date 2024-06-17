package logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

class LogFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {

        String result = "";

        Date date = new Date(record.getMillis());
        DateFormat dateFormat = new SimpleDateFormat("[dd-MM-yyyy HH:mm:ss]");
        result = result + dateFormat.format(date);

        result = result + " " + record.getLevel().toString();
        result = result + " " + record.getSourceClassName();
        result = result + ": " + record.getMessage();


        return result + "\n";
    }
}
