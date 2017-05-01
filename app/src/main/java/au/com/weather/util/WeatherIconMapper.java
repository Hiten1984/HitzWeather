package au.com.weather.util;


import android.net.Uri;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class is basically to fetch image from open weather using the icon code being returned from the json response
 * while fetching weather data of the city.
 */

public class WeatherIconMapper {
    private static String IMG_URL = "http://openweathermap.org/img/w/";

    public WeatherIconMapper() {
    }

    public Uri getImageUri(String code) {
        Uri uri = Uri.parse(IMG_URL + code + ".png");
        return uri;
    }

}
