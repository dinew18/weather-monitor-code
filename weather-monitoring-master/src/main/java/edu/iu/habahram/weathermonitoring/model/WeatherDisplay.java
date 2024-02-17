package edu.iu.habahram.weathermonitoring.model;

import org.springframework.stereotype.Component;

@Component
public class WeatherDisplay implements Observer, DisplayElement {
    private float avgTemperature;
    private float minTemperature = Float.MAX_VALUE; // Initialize to the highest possible value
    private float maxTemperature = Float.MIN_VALUE; // Initialize to the lowest possible value
    private int numberOfReadings; // Keep track of the number of readings for calculating the average
    private Subject weatherData;

    public WeatherDisplay(Subject weatherData) {
        this.weatherData = weatherData;
    }

    @Override
    public String display() {
        String html = "";
        html += String.format("<div style=\"background-image: " +
                "url(/images/sky.webp); " +
                "height: 400px; " +
                "width: 647.2px;" +
                "display:flex;flex-wrap:wrap;justify-content:center;align-content:center;" +
                "\">");
        html += "<section>";
        html += String.format("<label>Avg Temperature: %s</label><br />", avgTemperature);
        html += String.format("<label>Min Temperature: %s</label><br />", minTemperature);
        html += String.format("<label>Max Temperature: %s</label><br />", maxTemperature);
        html += "</section>";
        html += "</div>";
        return html;
    }

    @Override
    public String name() {
        return "Weather Display";
    }

    @Override
    public String id() {
        return "weather-display";
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        // Update the average, min, and max temperatures
        this.avgTemperature = (avgTemperature * numberOfReadings + temperature) / (numberOfReadings + 1);
        this.minTemperature = Math.min(minTemperature, temperature);
        this.maxTemperature = Math.max(maxTemperature, temperature);
        numberOfReadings++;
    }

    public void subscribe() {
        weatherData.registerObserver(this);
    }

    public void unsubscribe() {
        weatherData.removeObserver(this);
    }
}
