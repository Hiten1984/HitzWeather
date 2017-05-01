package au.com.weather.model;


import java.util.List;

import au.com.weather.json.CityDetailsJsonResponse;
import au.com.weather.json.TempListJsonResponse;

public class WeatherNextDaysSummaryData {

    String cod;
    CityDetailsJsonResponse cityDetails;
    List<TempListJsonResponse> mainList;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public CityDetailsJsonResponse getCityDetails() {
        return cityDetails;
    }

    public void setCityDetails(CityDetailsJsonResponse cityDetails) {
        this.cityDetails = cityDetails;
    }

    public List<TempListJsonResponse> getMainList() {
        return mainList;
    }

    public void setMainList(List<TempListJsonResponse> mainList) {
        this.mainList = mainList;
    }

}
