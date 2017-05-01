package au.com.weather.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherSummaryJsonResponse {
    @SerializedName("id")
    Long id;

    @SerializedName("name")
    String name;

    @SerializedName("city")
    private CityDetailsJsonResponse cityDetails;

    @SerializedName("main")
    private WeatherMainJsonResponse main;

    @SerializedName("list")
    private List<TempSummaryJsonResponse> mainList;

    @SerializedName("weather")
    private List<WeatherJsonResponse> weather;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CityDetailsJsonResponse getCityDetails() {
        return cityDetails;
    }

    public List<WeatherJsonResponse> getWeather() {
        return weather;
    }

    public List<TempSummaryJsonResponse> getMainList() {
        return mainList;
    }

    public WeatherMainJsonResponse getMain() {
        return main;
    }
}
