package com.example.repro;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class RedirectServerTestResource implements QuarkusTestResourceLifecycleManager {

    private HttpServer server;

    @Override
    public Map<String, String> start() {
        HttpServer httpServer;
        try {
            httpServer = HttpServer.create(new InetSocketAddress(0), 0);
        } catch (IOException e) {
            throw new RuntimeException("Failed to start test redirect server", e);
        }

        httpServer.createContext("/redirect", new RedirectHandler());
        httpServer.createContext("/target", new TextHandler("Hello World!"));
        httpServer.start();
        server = httpServer;

        return Map.of(
                "quarkus.rest-client.redirect-client.url",
                "http://localhost:" + httpServer.getAddress().getPort());
    }

    @Override
    public void stop() {
        if (server != null) {
            server.stop(0);
        }
    }

    private static final class RedirectHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"GET".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                exchange.close();
                return;
            }

            exchange.getResponseHeaders().add("Location", "/target");
            exchange.sendResponseHeaders(302, 0);
            exchange.close();
        }
    }

    private static final class TextHandler implements HttpHandler {

        private final byte[] bytes;

        private TextHandler(String body) {
            this.bytes = body.getBytes(StandardCharsets.UTF_8);
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Content-Type", "application/octet-stream");
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream output = exchange.getResponseBody()) {
                output.write(bytes);
            }
        }
    }
}
