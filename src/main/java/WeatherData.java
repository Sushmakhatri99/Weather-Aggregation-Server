package main.java;

public class WeatherData {
    private String id;
    private String name;
    private double airTemp;

    public WeatherData(String id, String name, double airTemp) {
        this.id = id;
        this.name = name;
        this.airTemp = airTemp;
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

    @Override
    public String toString() {
        return "WeatherData{id='" + id + "', name='" + name + "', air_temp=" + airTemp + "}";
    }
}
