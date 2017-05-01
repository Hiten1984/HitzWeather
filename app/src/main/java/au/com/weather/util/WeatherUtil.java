package au.com.weather.util;

import android.content.Context;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import au.com.weather.json.WeatherNextDaysSummaryJsonResponse;
import au.com.weather.json.WeatherSummaryJsonResponse;

public class WeatherUtil {

    /**
     *
     * @param context
     * @param path
     * @return WeatherSummaryJsonResponse
     *
     * Fetch the data for a city and map it to the desired response type.
     */
    public static WeatherSummaryJsonResponse fromJson(Context context, String path) {
        try {
            Gson gson = new Gson();
            Reader reader = getReader(context, path);
            return gson.fromJson(reader, WeatherSummaryJsonResponse.class);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     *
     * @param context
     * @param path
     * @return WeatherNextDaysSummaryJsonResponse
     *
     * Fetch the data for next few days of the city and map it to the desired response type.
     */
    public static WeatherNextDaysSummaryJsonResponse fromNextJson(Context context, String path) {
        Gson gson = new Gson();
        Reader reader = getReader(context, path);
        return gson.fromJson(reader, WeatherNextDaysSummaryJsonResponse.class);
    }

    private static Reader getReader(Context context, String path) {
        return new InputStreamReader(context.getResources().openRawResource(
                context.getResources().getIdentifier(path, "raw", context.getPackageName())
        ));
    }

    /**
     * Gets the current day in EEEE format.
     *
     * @return String
     */
    public static String getCurrentDay() {
        String day = "";
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        return day;
    }

}
