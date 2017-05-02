package au.com.weather.model;

import java.util.List;

import au.com.weather.json.WeatherJsonResponse;
import au.com.weather.json.WeatherMainJsonResponse;

public class WeatherSummaryData implements ResponseError{
    Long id;
    String name;
    WeatherMainJsonResponse main;
    List<WeatherJsonResponse> weather;
    String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WeatherMainJsonResponse getMain() {
        return main;
    }

    public void setMain(WeatherMainJsonResponse main) {
        this.main = main;
    }

    public List<WeatherJsonResponse> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherJsonResponse> weather) {
        this.weather = weather;
    }

    @Override
    public void onResponseError() {
        getMessage();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
