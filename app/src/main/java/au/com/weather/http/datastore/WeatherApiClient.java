package au.com.weather.http.datastore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import au.com.weather.json.WeatherSummaryJsonResponse;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherApiClient {

//    private String SERVICE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=london,uk&mode=json&units=metric&cnt=3&appid=8bd671c877c963200818696faaccab3a";
//    private String SERVICE_URL = "http://api.openweathermap.org/data/2.5/forecast?q=london,uk&mode=json&units=metric&cnt=3&appid=8bd671c877c963200818696faaccab3a";
    private static String SERVICE_URL = "http://api.openweathermap.org/data/2.5/";
    private static GetWeatherInfoByName service;
    private static Retrofit retrofit;

    public interface GetWeatherInfoByLataLng {
        @GET("/forecast")
        WeatherSummaryJsonResponse getWeatherInfo(@Query("lat") String latitiude,
                                                @Query("lng") String longitude,
                                                @Query("cnt") String cnt,
                                                @Query("appid") String appid,
                                                Callback<WeatherSummaryJsonResponse> weatherSummaryJsonResponseCallback);
    }

    public interface GetWeatherInfoByName {
//        @GET("/daily")
//        WeatherSummaryJsonResponse getCountries(@Query("q") String city, @Query("mode") String mode, @Query("units") String unit, @Query("appid") String id);
        @GET("/forecast")
        WeatherSummaryJsonResponse getWeatherInfo(@Query("q") String countryName,
                                                  @Query("cnt") String cnt,
                                                  @Query("mode") String mode,
                                                  @Query("units") String units,
                                                  @Query("appid") String appid);
//                                                  Callback<WeatherSummaryJsonResponse> weatherSummaryJsonResponseCallback);
    }

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(SERVICE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static GetWeatherInfoByName getCountryWeatherData() {
        Gson gson = new GsonBuilder().create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SERVICE_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        service = restAdapter.create(GetWeatherInfoByName.class);

        return service;
    }

}
