
import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class Main {

    public SerialPort serialPort;

    public static void main(String[] args) {

        SerialInterface serialInterface = new SerialInterface();
        ScriptManager scriptManager = new ScriptManager(serialInterface);
        WebServer webServer = new WebServer(scriptManager, serialInterface);

        scriptManager.start();
        webServer.start();
        serialInterface.run();

    }



}