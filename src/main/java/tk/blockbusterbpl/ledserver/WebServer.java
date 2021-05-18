package tk.blockbusterbpl.ledserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class WebServer {

    WebServer() {
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
        server.createContext("/GetState", new GetStateHandler());
        server.createContext("/init", new InitHandler());
        server.createContext("/exit", new ExitHandler());
        server.createContext("/GetHue", new GetHueHandler());
        server.createContext("/SetHue", new SetHueHandler());
        server.createContext("/GetSat", new GetSatHandler());
        server.createContext("/SetSat", new SetSatHandler());
        server.createContext("/GetVal", new GetValHandler());
        server.createContext("/SetVal", new SetValHandler());
        server.createContext("/GetOn", new GetOnHandler());
        server.createContext("/SetOn", new SetOnHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "pie";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class GetStateHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "";
            LEDState state = new LEDState(0, 0, 0);
            try {
                state = LEDController.getLedState();

            } catch (Exception e) {
                // TODO: handle exception
            }
            // LEDState state = LEDController.getLedState();
            // TODO Get state of LEDs
            Gson gson = new Gson();
            response = gson.toJson(state);
            System.out.println(response);
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
            String body = new BufferedReader(new InputStreamReader(bis, StandardCharsets.UTF_8)).lines()
                    .collect(Collectors.joining("\n"));
            System.out.println(body);
            Gson gson = new Gson();
            LEDState ledstate = gson.fromJson(body, LEDState.class);
            System.out.println("Hue: " + String.valueOf(ledstate.hue));
            System.out.println("Sat: " + String.valueOf(ledstate.sat));
            System.out.println("Val: " + String.valueOf(ledstate.val));

            String response = "Color Set";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

    }

    static class InitHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            LEDController.init(300);
            String response = "Initialization Successful";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class ExitHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "Exiting Program";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.exit(0);
        }
    }

    static class GetHueHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "";
            response = String.valueOf((int) LEDController.getLedHue());
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class SetHueHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            InputStream bis = t.getRequestBody();
            String body = new BufferedReader(new InputStreamReader(bis, StandardCharsets.UTF_8)).lines()
                    .collect(Collectors.joining("\n"));

            try {
                LEDController.setLedHue(Float.valueOf(body));

            } catch (Exception e) {
                // TODO: handle exception
            }
            // LEDController.setLedHue(Float.valueOf(body));
            System.out.println("Hue: " + String.valueOf(body));

            String response = "Color Set";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class GetSatHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "";
            response = String.valueOf((int) LEDController.getLedSat());
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class SetSatHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            InputStream bis = t.getRequestBody();
            String body = new BufferedReader(new InputStreamReader(bis, StandardCharsets.UTF_8)).lines()
                    .collect(Collectors.joining("\n"));
            try {
                LEDController.setLedSat(Float.valueOf(body));
            } catch (Exception e) {
                // TODO: handle exception
            }
            // LEDController.setLedSat(Float.valueOf(body));
            System.out.println("Sat: " + String.valueOf(body));

            String response = "Color Set";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class GetValHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "";
            response = String.valueOf((int) LEDController.getLedVal());
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class SetValHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            InputStream bis = t.getRequestBody();
            String body = new BufferedReader(new InputStreamReader(bis, StandardCharsets.UTF_8)).lines()
                    .collect(Collectors.joining("\n"));
            try {
                LEDController.setLedVal(Float.valueOf(body));
            } catch (Exception e) {
                // TODO: handle exception
            }
            // LEDController.setLedVal(Float.valueOf(body));
            System.out.println("Val: " + String.valueOf(body));

            String response = "Color Set";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class GetOnHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "";
            response = String.valueOf(LEDController.getOnState());
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class SetOnHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            InputStream bis = t.getRequestBody();
            String body = new BufferedReader(new InputStreamReader(bis, StandardCharsets.UTF_8)).lines()
                    .collect(Collectors.joining("\n"));
            try {
                LEDController.setOnState(Boolean.valueOf(body));
            } catch (Exception e) {
                // TODO: handle exception
            }
            // LEDController.setLedVal(Float.valueOf(body));
            System.out.println("On: " + String.valueOf(body));

            String response = "On State Set";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}
