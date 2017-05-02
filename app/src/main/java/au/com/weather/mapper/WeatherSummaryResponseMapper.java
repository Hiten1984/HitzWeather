package au.com.weather.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.weather.model.CityDetailsData;
import au.com.weather.model.WeatherNextDaysSummaryData;
import au.com.weather.json.CityDetailsJsonResponse;
import au.com.weather.json.TempSummaryJsonResponse;
import au.com.weather.json.WeatherJsonResponse;
import au.com.weather.json.WeatherMainJsonResponse;
import au.com.weather.json.WeatherNextDaysSummaryJsonResponse;
import au.com.weather.json.WeatherSummaryJsonResponse;
import au.com.weather.model.TempData;
import au.com.weather.model.WeatherData;
import au.com.weather.model.WeatherMainData;
import au.com.weather.model.WeatherSummaryData;
import retrofit2.Response;

public class WeatherSummaryResponseMapper {

    public WeatherSummaryResponseMapper() {
    }

    /**
     *
     * @param response
     * @return WeatherSummaryData
     *
     * This is a mapper function for fetching weather by city.
     */
    public WeatherSummaryData initContent(WeatherSummaryJsonResponse response) {
        WeatherSummaryData summaryData = new WeatherSummaryData();
        if(response != null) {
            summaryData.setId(response.getId());
            summaryData.setName(response.getName());
            summaryData.setMain(mapMainWeatherData(response.getMain()));
            summaryData.setWeather(mapWeatherListData(response.getWeather()));
        }
        return summaryData;
    }

    private CityDetailsJsonResponse mapCityDetailsResponse(CityDetailsJsonResponse cityDetails) {
        CityDetailsData cityData = new CityDetailsData();
        cityData.setName(cityDetails.getName());
        cityData.setId(cityDetails.getId());
        return cityData;
    }

    private List<TempSummaryJsonResponse> mapMainTempList(List<TempSummaryJsonResponse> mainListResponse) {
        List<TempSummaryJsonResponse> tempList = new ArrayList<>();
        for(TempSummaryJsonResponse tempResponse: mainListResponse) {
            TempData tempData = new TempData();
            tempData.setMin(tempResponse.getMin());
            tempData.setMax(tempResponse.getMax());
            tempData.setPressure(tempResponse.getPressure());
            tempData.setHumidity(tempResponse.getHumidity());
            tempData.setWeatherJsonResponse(mapWeatherListData(tempResponse.getWeather()));
            tempList.add(tempData);
        }
        return tempList;
    }

    private List<WeatherMainJsonResponse> mapMainWeatherListData(List<WeatherMainJsonResponse> weather) {
        List<WeatherMainJsonResponse> mainList = new ArrayList<>();
        for(WeatherMainJsonResponse response : weather) {
            WeatherMainData mainData = new WeatherMainData();
            mainData.setTemp(response.getTemp());
            mainData.setHumidity(response.getHumidity());
            mainData.setPressure(response.getPressure());
            mainData.setTempMin(response.getTempMin());
            mainData.setTempMax(response.getTempMax());
            mainList.add(mainData);
        }
        return mainList;
    }

    private WeatherMainJsonResponse mapMainWeatherData(WeatherMainJsonResponse mainResponse) {
        WeatherMainData mainData = new WeatherMainData();
        mainData.setTemp(mainResponse.getTemp());
        mainData.setHumidity(mainResponse.getHumidity());
        mainData.setPressure(mainResponse.getPressure());
        mainData.setTempMin(mainResponse.getTempMin());
        mainData.setTempMax(mainResponse.getTempMax());
        return mainData;
    }

    private List<WeatherJsonResponse> mapWeatherListData(List<WeatherJsonResponse> weather) {
        List<WeatherJsonResponse> dataList = new ArrayList<>();
        for(WeatherJsonResponse weatherResponse : weather) {
            WeatherData data = new WeatherData();
            data.setId(weatherResponse.getId());
            data.setMain(weatherResponse.getMain());
            data.setDescription(weatherResponse.getDescription());
            data.setIcon(weatherResponse.getIcon());
            dataList.add(data);
        }
        return dataList;
    }

    private WeatherJsonResponse mapWeatherData(WeatherJsonResponse weatherResponse) {
        WeatherData data = new WeatherData();
            data.setId(weatherResponse.getId());
            data.setMain(weatherResponse.getMain());
            data.setDescription(weatherResponse.getDescription());
            data.setIcon(weatherResponse.getIcon());
        return data;
    }

    /**
     *
     * @param response
     * @return WeatherNextDaysSummaryData
     *
     * * This is a mapper function for mapping weather stub data for next few days....
     */
    public WeatherNextDaysSummaryData initNextDaysContent(WeatherNextDaysSummaryJsonResponse response) {
        WeatherNextDaysSummaryData nextDaysData = new WeatherNextDaysSummaryData();
        nextDaysData.setCod(response.getCod());
        nextDaysData.setCityDetails(mapCityDetailsResponse(response.getCityDetails()));
        nextDaysData.setMainList(response.getTempList());
        return nextDaysData;
    }

    /**
     *
     * @param response
     * @return Response<WeatherNextDaysSummaryJsonResponse>
     *
     * * This is a mapper function for mapping weather data for next few days from the API....
     */
    public WeatherNextDaysSummaryData initNextDaysContent(Response<WeatherNextDaysSummaryJsonResponse> response) {
        WeatherNextDaysSummaryData nextDaysData = new WeatherNextDaysSummaryData();
        if(response.body() != null) {
            nextDaysData.setCod(response.body().getCod());
            nextDaysData.setCityDetails(mapCityDetailsResponse(response.body().getCityDetails()));
            nextDaysData.setMainList(response.body().getTempList());
        }
        return nextDaysData;
    }

    public WeatherSummaryData initWeatherContent(Response<WeatherSummaryJsonResponse> response) {
        WeatherSummaryData summaryData = new WeatherSummaryData();
        if(response.body() != null) {
            summaryData.setId(response.body().getCityDetails().getId());
            summaryData.setName(response.body().getCityDetails().getName());
            summaryData.setMain(mapMainWeatherData(response.body().getMainList().get(0).getMain()));
            summaryData.setWeather(mapWeatherListData(response.body().getMainList().get(0).getWeather()));
        } else {
            summaryData.setMessage(response.errorBody().toString());
        }
        return summaryData;
    }
}
