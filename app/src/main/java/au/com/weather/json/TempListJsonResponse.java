package au.com.weather.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TempListJsonResponse {

    @SerializedName("dt")
    long date;

    @SerializedName("temp")
    private TempSummaryJsonResponse tempJsonResponse;

    @SerializedName("weather")
    private List<WeatherJsonResponse> weatherJsonResponse;

    public long getDate() {
        return date;
    }

    public TempSummaryJsonResponse getTempJsonResponse() {
        return tempJsonResponse;
    }

    public List<WeatherJsonResponse> getWeatherJsonResponse() {
        return weatherJsonResponse;
    }
}
