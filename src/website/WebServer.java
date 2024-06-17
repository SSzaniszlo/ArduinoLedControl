import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebServer extends Thread{

    final int PORT = 6969;

    ScriptManager scriptManager;
    SerialInterface serial;

    private static final Logger LOGGER = Logger.getLogger(WebServer.class.getName());


    public WebServer(ScriptManager scriptManager, SerialInterface serial) {
        this.scriptManager = scriptManager;
        this.serial = serial;

        LOGGER.addHandler(new LogHandler());
    }

    public void run() {
        try {

            LOGGER.log(Level.INFO, "Creating web server...");

            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.createContext("/").setHandler(new WebHandler(scriptManager, serial));
            server.start();

            LOGGER.log(Level.INFO, "Web server started on port " + PORT);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }
}

