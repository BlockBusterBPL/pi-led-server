package tk.blockbusterbpl.ledserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;

import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class WebServer {

    WebServer(){
        try {
            main();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void main() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
        server.createContext("/test", new MyHandler());
        server.createContext("/SetColor", new SetColHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "Test Response!";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class SetColHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            InputStream bis = t.getRequestBody();
            String body = new BufferedReader(
                new InputStreamReader(bis, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
            System.out.println(body);
            Gson gson = new Gson();
            LEDState ledstate = gson.fromJson(body, LEDState.class);
            StringBuilder sb = new StringBuilder(100);
            System.out.println(String.valueOf(ledstate.red));
            System.out.println(String.valueOf(ledstate.green));
            System.out.println(String.valueOf(ledstate.blue));

            String response = "Color Set";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
        
    }

}


