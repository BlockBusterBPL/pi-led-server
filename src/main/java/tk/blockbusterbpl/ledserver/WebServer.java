package tk.blockbusterbpl.ledserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.sun.net.httpserver.*;

import org.apache.commons.text.StringEscapeUtils;

public class WebServer {
    HttpServer server;

    WebServer() {
        init();
    }

    public void init() {
        try {
            server.create(new InetSocketAddress("localhost", 42069), 0);
        } catch (IOException e) {
            // TO DO Auto-generated catch block
            e.printStackTrace();
        }
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor)Executors.newFixedThreadPool(10);
        //server.createContext("/test", new  MyHttpHandler());
        server.setExecutor(threadPoolExecutor);
        server.start();
        System.out.println("Server started on port 42069");
    }
}
