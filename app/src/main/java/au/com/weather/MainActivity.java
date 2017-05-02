package au.com.weather;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import au.com.weather.http.datastore.WeatherApiClient;
import au.com.weather.http.datastore.WeatherDataStore;
import au.com.weather.json.TempListJsonResponse;
import au.com.weather.json.WeatherNextDaysSummaryJsonResponse;
import au.com.weather.json.WeatherSummaryJsonResponse;
import au.com.weather.mapper.WeatherSummaryResponseMapper;
import au.com.weather.model.WeatherNextDaysSummaryData;
import au.com.weather.model.WeatherSummaryData;
import au.com.weather.util.WeatherIconMapper;
import au.com.weather.util.WeatherUtil;
import au.com.weather.view.adapter.BaseRecyclerAdapter;
import au.com.weather.view.adapter.NextDayWeatherAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import weather.hitz.app.co.weather.R;

public class MainActivity extends AppCompatActivity {

    //URL - http://api.openweathermap.org/data/2.5/forecast/daily?q=london,uk&mode=json&units=metric&cnt=3&appid=8bd671c877c963200818696faaccab3a

    @Bind(R.id.enter_city_name)
    EditText enterCityName;
    @Bind(R.id.search_container)
    RelativeLayout container;
    @Bind(R.id.city_name)
    TextView cityName;
    @Bind(R.id.current_day)
    TextView currentDay;
    @Bind(R.id.weather_icon)
    ImageView weatherIcon;
    @Bind(R.id.temp_container)
    LinearLayout tempContainer;
    @Bind(R.id.max_temp)
    TextView maxTemperature;
    @Bind(R.id.min_temp)
    TextView minTemperature;
    @Bind(R.id.current_temp)
    TextView currentTemperature;
    @Bind(R.id.current_humidity)
    TextView humidity;
    @Bind(R.id.current_pressure)
    TextView pressure;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.horizontal_view)
    View horizontalView;

    private WeatherSummaryData weatherSummaryData;
    WeatherNextDaysSummaryData weatherNextDaysSummaryData;
    WeatherSummaryResponseMapper mapper;
    private NextDayWeatherAdapter adapter;
    private WeatherIconMapper weatherIconMapper;
    private static final String API_KEY = "8bd671c877c963200818696faaccab3a";
    private WeatherDataStore dataStore;
    private Call<WeatherSummaryJsonResponse> dailyWeatherDataResponse;
    private Call<WeatherNextDaysSummaryJsonResponse> nextDaysWeatherDataResponse;
    private String city;
    private boolean isVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mapper = new WeatherSummaryResponseMapper();
        dataStore = WeatherApiClient.getClient().create(WeatherDataStore.class);
        Intent intent = getIntent();
        if(intent != null) {
            city = intent.getStringExtra("city_name");
            initApi(city);
        }
    }

    /**
     * on click for search button.
     */
    @OnClick(R.id.search_city_name)
    public void searchCity() {
        initApi(enterCityName.getText().toString().toLowerCase());

//weatherSummaryData = mapper.initContent(WeatherUtil.fromJson(getApplicationContext(), enterCityName.getText().toString().toLowerCase()));
//        if (weatherSummaryData.getName() != null) {
//            //setUpData(true);
//            setupAdapter();
//            container.setVisibility(View.GONE);
//            hideKeyboardForView(enterCityName);
//        } else {
//            Toast.makeText(this, R.string.weather_data_not_found, Toast.LENGTH_LONG).show();
//            hideKeyboardForView(enterCityName);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (item.getItemId() == R.id.change_city) {
            showInputDialog();
        }
        return false;

    }

    /**
     * THis method is used to show change city dialog
     */
    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.change_city);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton(R.string.search_city, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeCity(input.getText().toString());
            }
        });
        builder.show();
    }

    /**
     * @param cityName This is method used to change city name from menu options.
     */
    private void changeCity(String cityName) {
//        weatherSummaryData = mapper.initContent(WeatherUtil.fromJson(getApplicationContext(), cityName.toLowerCase()));
//        setUpData(true);
        initApi(cityName);
    }

    /**
     * @param weatherSummaryData dailyWeatherDataResponse coming from the webservice for the entered City Name
     **/
    private void setUpData(WeatherSummaryData weatherSummaryData) {
        if (weatherSummaryData != null && weatherSummaryData.getMessage() != null) {
            showUI(false);
            isVisible = false;
            Toast.makeText(this, "Not a valid address", Toast.LENGTH_LONG).show();
        }
        if (weatherSummaryData != null) {
            weatherNextDaysSummaryData = mapper.initNextDaysContent(WeatherUtil.fromNextJson(getApplicationContext(), "london_3_days"));
            if(isVisible)
                showUI(true);
            cityName.setText(weatherSummaryData.getName());
            currentDay.setText(WeatherUtil.getCurrentDay());
            if (weatherSummaryData.getMain() != null) {
                maxTemperature.setText(weatherSummaryData.getMain().getTempMax() + " \u2103");
                minTemperature.setText(weatherSummaryData.getMain().getTempMin() + " \u2103");
                humidity.setText(weatherSummaryData.getMain().getHumidity() + "%");
                pressure.setText(weatherSummaryData.getMain().getPressure() + " hPa");
            }
            if (weatherSummaryData.getWeather() != null) {
                currentTemperature.setText(weatherSummaryData.getWeather().get(0).getMain());
                weatherIconMapper = new WeatherIconMapper();
                Picasso.with(getApplicationContext())
                        .load(weatherIconMapper.getImageUri(weatherSummaryData.getWeather().get(0).getIcon()))
                        .noFade()
                        .into(weatherIcon);
            }
        } else {
            Toast.makeText(getBaseContext(), "City not found", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param view This is for hiding the keyboard.
     */
    private void hideKeyboardForView(View view) {
        final InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     *
     * @param cityName -- call the rest api with city name
     *                 This method is to call the api and get the forecast for the same day and next 10 days.
     */
    public void initApi(String cityName) {
        dailyWeatherDataResponse = dataStore.getWeatherByCityName(cityName,
                "json",
                "metric",
                API_KEY);
        dailyWeatherDataResponse.enqueue(new retrofit2.Callback<WeatherSummaryJsonResponse>() {
            @Override
            public void onResponse(Call<WeatherSummaryJsonResponse> call, retrofit2.Response<WeatherSummaryJsonResponse> weatherSummaryJsonResponse) {
                Log.d("Hiten", "success" + weatherSummaryJsonResponse);
                weatherSummaryData = mapper.initWeatherContent(weatherSummaryJsonResponse);
                setUpData(weatherSummaryData);
            }

            @Override
            public void onFailure(Call<WeatherSummaryJsonResponse> call, Throwable error) {
                Log.d("Hiten", "failure", error);
            }
        });

//        nextDaysWeatherDataResponse = dataStore.getNextDaysWeatherByCityName(enterCityName.getText().toString().toLowerCase(),
        nextDaysWeatherDataResponse = dataStore.getNextDaysWeatherByCityName(cityName,
                "10",
                "json",
                "metric",
                API_KEY);

        nextDaysWeatherDataResponse.enqueue(new Callback<WeatherNextDaysSummaryJsonResponse>() {
            @Override
            public void onResponse(Call<WeatherNextDaysSummaryJsonResponse> call, Response<WeatherNextDaysSummaryJsonResponse> response) {
                weatherNextDaysSummaryData = mapper.initNextDaysContent(response);
                if(weatherNextDaysSummaryData.getMainList() != null)
                    setupAdapter(weatherNextDaysSummaryData);
            }

            @Override
            public void onFailure(Call<WeatherNextDaysSummaryJsonResponse> call, Throwable t) {

            }
        });
    }

    /**
     * This method is basically to set up the layout for horizontal list for next few days of weather.
     * The list is set horizontally and the data after being fetched has been set.
     *
     * On click of an item it currently shows a toast giving the information about weather.
     */
    public void setupAdapter(WeatherNextDaysSummaryData weatherNextDaysSummaryData) {
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        adapter = new NextDayWeatherAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);
        adapter.setWeatherData(weatherNextDaysSummaryData.getMainList());
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view1, int position, BaseRecyclerAdapter.ViewHolder viewHolder) {
                TempListJsonResponse item = adapter.getWeatherItem(position);
                Toast.makeText(getBaseContext(), "Item Data Description - "+item.getWeatherJsonResponse().get(0).getMain() + ", min temp - " + item.getTempJsonResponse().getMin() , Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showUI(boolean visible) {
        cityName.setVisibility(visible ? View.VISIBLE : View.GONE);
        currentDay.setVisibility(visible ? View.VISIBLE : View.GONE);
        tempContainer.setVisibility(visible ? View.VISIBLE : View.GONE);
        weatherIcon.setVisibility(visible ? View.VISIBLE : View.GONE);
        horizontalView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

}
