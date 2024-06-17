import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class ScriptManager extends Thread {

    SerialInterface serial;

    Process currentProcess;
    boolean doScript = true;


    public ScriptManager(SerialInterface serial) {
        this.serial = serial;
    }


    public void run() {

        try {

            while (true) {

                if (doScript) {

                    currentProcess = Runtime.getRuntime().exec("python3 scripts/main.py");

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

                    doScript = false;

                } else {
                    if (Thread.interrupted()) {
                        System.out.println("This shouldn't print");
                        doScript = true;
                    }
                }

            }



        } catch(IOException e){
            throw new RuntimeException(e);
        }

    }
}
