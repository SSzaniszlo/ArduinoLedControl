package arduino;

import com.fazecast.jSerialComm.SerialPort;
import logging.LogHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerialInterface {

    private OutputStream outputStream;

    private static final Logger LOGGER = Logger.getLogger(SerialInterface.class.getName());

    public SerialInterface() {
        LOGGER.addHandler(new LogHandler());
    }

    public void run() {


        LOGGER.log(Level.INFO, "Initialising serial port at 9600");


        SerialPort serialPort = SerialPort.getCommPort("/dev/ttyACM0");
        serialPort.setBaudRate(9600);
        serialPort.openPort();

        LOGGER.log(Level.INFO, "Serial port opened");

        outputStream = serialPort.getOutputStream();
    }

    public void write(byte[] data) {

        data[0] = (byte) (255 - (int)data[0]);
        data[1] = (byte) (255 - (int)data[1]);
        data[2] = (byte) (255 - (int)data[2]);


        try {
            outputStream.write(data);
            outputStream.flush();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

    }


}
