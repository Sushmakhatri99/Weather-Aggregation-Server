package main.java;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.concurrent.Executors; // Import for Executors for threading
import java.nio.charset.StandardCharsets;

public class AggregationServer {

    private Map<String, WeatherData> dataStore;
    private LamportClock lamportClock;

    public AggregationServer() {
        dataStore = new HashMap<>();
        lamportClock = new LamportClock();
        startDataExpiryChecker(); // Starts background thread for expiry
    }

    // Method to handle PUT requests
    public void handlePUT(String jsonData) {
        lamportClock.increment();
        WeatherData data = parseWeatherData(jsonData);
        dataStore.put(data.getId(), data);
        System.out.println("Data added: " + data.toString());
    }

    // Method to handle GET requests
    public WeatherData handleGET(String id) {
        lamportClock.increment();
        return dataStore.get(id);
    }

    // Parses incoming JSON string into a WeatherData object
    private WeatherData parseWeatherData(String jsonData) {
        // This is a simplified parsing for demonstration.
        String[] parts = jsonData.split(",");
        String id = parts[0].split(":")[1].replace("\"", "").trim();
        String name = parts[1].split(":")[1].replace("\"", "").trim();
        double airTemp = Double.parseDouble(parts[2].split(":")[1].replace("}", "").trim());
        return new WeatherData(id, name, airTemp);
    }

    // Starts a background thread to check for expired data every 10 seconds
    private void startDataExpiryChecker() {
        Runnable task = () -> {
            while (true) {
                try {
                    Thread.sleep(10000); // Check every 10 seconds
                    long currentTime = System.currentTimeMillis();
                    Iterator<Map.Entry<String, WeatherData>> iterator = dataStore.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, WeatherData> entry = iterator.next();
                        if (currentTime - entry.getValue().getLastUpdated() > 30000) { // If older than 30 seconds
                            iterator.remove(); // Remove expired data
                            System.out.println("Data expired and removed for ID: " + entry.getKey());
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true); // Daemon thread, so it ends with the program
        thread.start();
    }

    public static void main(String[] args) throws IOException {
        AggregationServer server = new AggregationServer();
        System.out.println("Weather Aggregation Server is running");

        // Create an HTTP server on port 4567
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(4567), 0);
        httpServer.createContext("/weather", new WeatherHandler(server));

        // Creates a thread pool to handle multiple requests
        httpServer.setExecutor(Executors.newFixedThreadPool(10)); 
        httpServer.start();
    }

    // Custom handler for processing HTTP requests
    static class WeatherHandler implements HttpHandler {
        private final AggregationServer server;

        public WeatherHandler(AggregationServer server) {
            this.server = server;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            if (method.equalsIgnoreCase("PUT")) {
                InputStream inputStream = exchange.getRequestBody();
                byte[] inputData = inputStream.readAllBytes();
                String jsonData = new String(inputData, StandardCharsets.UTF_8);

                // Handle PUT request
                server.handlePUT(jsonData);

                String response = "Weather data added successfully";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();
            } else if (method.equalsIgnoreCase("GET")) {
                String id = exchange.getRequestURI().getPath().split("/")[2]; // Extract the ID from the URL
                WeatherData data = server.handleGET(id);

                String response = data != null ? data.toString() : "No data found";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();
            } else {
                String response = "Unsupported method";
                exchange.sendResponseHeaders(405, response.length());
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();
            }
        }
    }
}
