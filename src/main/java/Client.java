package main.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Client {

    public static void sendGET() throws Exception {
        @SuppressWarnings("deprecation")
        URL url = new URL("http://localhost:4567/weather/IDS60901");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) { // Success
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println("Response: " + response.toString());
        } else {
            System.out.println("GET request failed. Response Code: " + responseCode);
        }
    }

    public static void main(String[] args) {
        try {
            sendGET();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
