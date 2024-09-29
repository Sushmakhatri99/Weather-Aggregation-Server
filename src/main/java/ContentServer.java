package main.java;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ContentServer {

    private static final String PUT_URL = "http://localhost:4567/weather";

    public static void sendPUT(String jsonInputString) throws Exception {
        @SuppressWarnings("deprecation")
        URL url = new URL(PUT_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("PUT");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");

        try (OutputStream os = httpURLConnection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = httpURLConnection.getResponseCode();
        System.out.println("Response Code: " + responseCode);
    }

    public static void main(String[] args) {
        String weatherData = "{ \"id\":\"IDS60901\", \"name\":\"Adelaide\", \"air_temp\":13.3 }";

        try {
            sendPUT(weatherData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
