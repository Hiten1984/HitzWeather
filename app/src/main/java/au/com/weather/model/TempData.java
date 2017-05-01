package au.com.weather.model;

import java.util.List;

import au.com.weather.json.TempSummaryJsonResponse;
import au.com.weather.json.WeatherJsonResponse;
import au.com.weather.json.WeatherMainJsonResponse;

public class TempData extends TempSummaryJsonResponse {
    double min;
    double max;
    double pressure;
    double humidity;
    List<WeatherJsonResponse> weatherJsonResponse;

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public List<WeatherJsonResponse> getWeatherJsonResponse() {
        return weatherJsonResponse;
    }

    public void setWeatherJsonResponse(List<WeatherJsonResponse> weatherJsonResponse) {
        this.weatherJsonResponse = weatherJsonResponse;
    }
}
