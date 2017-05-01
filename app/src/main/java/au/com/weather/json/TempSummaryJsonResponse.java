package au.com.weather.json;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TempSummaryJsonResponse {

    @SerializedName("min")
    double min;

    @SerializedName("max")
    double max;

    @SerializedName("pressure")
    double pressure;

    @SerializedName("humidity")
    double humidity;

    @SerializedName("main")
    private WeatherMainJsonResponse main;

    @SerializedName("weather")
    private List<WeatherJsonResponse> weather = new ArrayList<>();

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public WeatherMainJsonResponse getMain() {
        return main;
    }

    public List<WeatherJsonResponse> getWeather() {
        return weather;
    }
}
