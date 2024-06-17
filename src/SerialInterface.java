import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

public class SerialInterface {

    private OutputStream outputStream;

    public void run() {
        SerialPort serialPort = SerialPort.getCommPort("/dev/ttyACM0");
        serialPort.setBaudRate(9600);
        serialPort.openPort();

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
            throw new RuntimeException(e);
        }

    }


}
