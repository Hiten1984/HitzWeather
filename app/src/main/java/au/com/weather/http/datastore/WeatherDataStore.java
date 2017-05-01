package au.com.weather.http.datastore;

import au.com.weather.json.WeatherNextDaysSummaryJsonResponse;
import au.com.weather.json.WeatherSummaryJsonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WeatherDataStore {

    String DAILY = "forecast/daily";
    String FORECAST = "forecast";

    @GET(FORECAST)
    Call<WeatherSummaryJsonResponse> getWeatherByCityName(@Query("q") String countryName, @Query("mode") String mode, @Query("units") String units, @Query("appid") String appid);

    @GET(DAILY)
    Call<WeatherNextDaysSummaryJsonResponse> getNextDaysWeatherByCityName(@Query("q") String countryName, @Query("cnt") String cnt, @Query("mode") String mode, @Query("units") String units, @Query("appid") String appid);
}
