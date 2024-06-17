
import arduino.SerialInterface;
import com.fazecast.jSerialComm.SerialPort;
import logging.LogHandler;
import scripting.ScriptManager;
import website.WebServer;

import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public SerialPort serialPort;

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static BlockingQueue<String> logQueue = new LinkedBlockingDeque<>();


    public static void main(String[] args) throws IOException {

        LOGGER.addHandler(new LogHandler());

        LOGGER.log(Level.INFO, "Starting program...");

        SerialInterface serialInterface = new SerialInterface();
        ScriptManager scriptManager = new ScriptManager(serialInterface);
        WebServer webServer = new WebServer(scriptManager, serialInterface);

        scriptManager.start();
        webServer.start();
        serialInterface.run();

        File logFile = new File("test.log");

        while (true) {
            if (!logQueue.isEmpty()) {
                try {
                    FileWriter fileWriter = new FileWriter(logFile, true);
                    fileWriter.append(logQueue.take());
                    fileWriter.close();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }



}