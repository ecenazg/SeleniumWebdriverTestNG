package org.example;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;

public class SimpleHttpServer {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new StaticFileHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server started on port 8000");
    }

    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String root = "src/main/static/login.html";
            String filePath = root + t.getRequestURI().getPath();
            File file = new File(filePath);
            if (!file.exists()) {
                String response = "404 (Not Found)\n";
                t.sendResponseHeaders(404, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                t.sendResponseHeaders(200, file.length());
                OutputStream os = t.getResponseBody();
                FileInputStream fs = new FileInputStream(file);
                final byte[] buffer = new byte[0x10000];
                int count;
                while ((count = fs.read(buffer)) >= 0) {
                    os.write(buffer, 0, count);
                }
                fs.close();
                os.close();
            }
        }
    }
}
