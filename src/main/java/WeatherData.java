package main.java;

public class WeatherData {
    private String id;
    private String name;
    private double airTemp;
    private long lastUpdated; // Store the timestamp of the last update

    public WeatherData(String id, String name, double airTemp) {
        this.id = id;
        this.name = name;
        this.airTemp = airTemp;
        this.lastUpdated = System.currentTimeMillis(); // Set last updated time to current time
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAirTemp() {
        return airTemp;
    }

    public void setAirTemp(double airTemp) {
        this.airTemp = airTemp;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    // Override toString method for easier logging/debugging
    @Override
    public String toString() {
        return "WeatherData{id='" + id + "', name='" + name + "', air_temp=" + airTemp + "}";
    }
}
