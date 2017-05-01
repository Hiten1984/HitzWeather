package au.com.weather.json;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WeatherNextDaysSummaryJsonResponse {

    @SerializedName("cod")
    private String cod;

    @SerializedName("city")
    private CityDetailsJsonResponse cityDetails;

    @SerializedName("list")
    private List<TempListJsonResponse> tempList = new ArrayList<>();

    public String getCod() {
        return cod;
    }

    public CityDetailsJsonResponse getCityDetails() {
        return cityDetails;
    }

    public List<TempListJsonResponse> getTempList() {
        return tempList;
    }

}
