import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScriptManager extends Thread {

    SerialInterface serial;

    Process currentProcess;
    boolean doScript = true;


    private static final Logger LOGGER = Logger.getLogger(ScriptManager.class.getName());

    public ScriptManager(SerialInterface serial) {
        this.serial = serial;
        LOGGER.addHandler(new LogHandler());
    }


    public void run() {

            while (true) {

                try {

                    if (doScript) {

                        currentProcess = Runtime.getRuntime().exec("python3 scripts/main.py");

                        LOGGER.log(Level.INFO, "Started python script");

                        InputStream inputStream = currentProcess.getInputStream();
                        Scanner scanner = new Scanner(inputStream);

                        while (currentProcess.isAlive()) {

                            if (Thread.interrupted()) {
                                currentProcess.destroy();
                                break;
                            }


                            String inputString = scanner.next();

                            String[] stringArray = inputString.split(":");

                            byte r = (byte) Integer.parseInt(stringArray[0]);
                            byte g = (byte) Integer.parseInt(stringArray[1]);
                            byte b = (byte) Integer.parseInt(stringArray[2]);
                            int sleep = Integer.parseInt(stringArray[3]);


                            serial.write(new byte[]{r, g, b});

                            try {
                                Thread.sleep(sleep);
                            } catch (InterruptedException e) {
                                break;
                            }

                        }

                        LOGGER.log(Level.WARNING, "Process exited");

                        doScript = false;

                    } else {
                        if (Thread.interrupted()) {
                            LOGGER.log(Level.INFO, "Received request to start script");
                            doScript = true;
                        }
                    }

                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                }

            }





    }
}
