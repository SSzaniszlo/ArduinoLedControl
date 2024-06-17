import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebServer extends Thread{

    final int PORT = 6969;

    ScriptManager scriptManager;
    SerialInterface serial;

    public WebServer(ScriptManager scriptManager, SerialInterface serial) {
        this.scriptManager = scriptManager;
        this.serial = serial;
    }

    public void run() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.createContext("/").setHandler(new WebHandler(scriptManager, serial));
            server.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

