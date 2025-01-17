package website;

import arduino.SerialInterface;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import logging.LogHandler;
import scripting.ScriptManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

class WebHandler implements HttpHandler {

    ScriptManager scriptManager;
    SerialInterface serial;

    final String PREFIX = "website";

    private static final Logger LOGGER = Logger.getLogger(WebHandler.class.getName());

    public WebHandler(ScriptManager scriptManager, SerialInterface serial) {
        this.scriptManager = scriptManager;
        this.serial = serial;

        LOGGER.addHandler(new LogHandler());
    }


    @Override
    public void handle(HttpExchange exchange) {

        String method = exchange.getRequestMethod();

        switch (method) {
            case "GET":
                getRequest(exchange);
                break;
            case "POST":
                postRequest(exchange);
                break;


            default:
                System.out.println("What?");
        }
    }


    public void getRequest(HttpExchange exchange) {

        LOGGER.log(Level.INFO, "Received GET request from " + exchange.getRemoteAddress().toString());

        String requestPath = exchange.getRequestURI().toString();

        if (requestPath.equals("/")) {
            requestPath = "/index.html";
        }

        OutputStream outputStream = exchange.getResponseBody();

        byte[] response;
        int code;


        try {
            File file = new File(PREFIX + requestPath);
            Scanner scanner = new Scanner(file).useDelimiter("\\Z");

            String responseString = scanner.next();
            response = responseString.getBytes(StandardCharsets.UTF_8);
            code = 200;

        } catch (FileNotFoundException e) {
            String responseString = "404 not found: " + e.toString();
            response = responseString.getBytes(StandardCharsets.UTF_8);
            code = 404;
        }

        try {
            exchange.sendResponseHeaders(code, response.length);
            outputStream.write(response);
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        exchange.close();

    }

    public void postRequest(HttpExchange exchange) {

        LOGGER.log(Level.INFO, "Received POST request from " + exchange.getRemoteAddress().toString());

        try {

            InputStream inputStream = exchange.getRequestBody();
            OutputStream outputStream = exchange.getResponseBody();

            Scanner scanner = new Scanner(inputStream);
            String request = scanner.next();
            System.out.println(request);

            byte[] response;

            if (request.equalsIgnoreCase("stop")) {

                LOGGER.log(Level.INFO, "Interrupting script manager thread");

                scriptManager.interrupt();
                response = "Interrupted manager thread".getBytes(StandardCharsets.UTF_8);



            } else if (request.contains(":")) {

                String[] stringArray = request.split(":");

                byte r = (byte) Integer.parseInt(stringArray[0]);
                byte g = (byte) Integer.parseInt(stringArray[1]);
                byte b = (byte) Integer.parseInt(stringArray[2]);

                System.out.println(r);
                System.out.println(g);
                System.out.println(b);

                serial.write(new byte[]{r, g, b});

                response = "Sent colour".getBytes(StandardCharsets.UTF_8);


            } else {
                response = "Dunno what u just send but ok bruh".getBytes();
            }



            exchange.sendResponseHeaders(200, response.length);

            outputStream.write(response);
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        exchange.close();
    }


}
