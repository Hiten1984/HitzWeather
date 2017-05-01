package au.com.weather.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import au.com.weather.json.TempListJsonResponse;
import au.com.weather.util.WeatherIconMapper;
import butterknife.Bind;
import butterknife.ButterKnife;
import weather.hitz.app.co.weather.R;

/**
 * This is the adapter class for setting up the View Holder and binding the view holder with the data.
 */

public class NextDayWeatherAdapter extends BaseRecyclerAdapter {

    private final Context context;
    List<TempListJsonResponse> data;
    private WeatherIconMapper weatherIconMapper;

    public NextDayWeatherAdapter(Context context) {
        this.context = context;
        weatherIconMapper = new WeatherIconMapper();
    }

    @Override
    public WeatherEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.next_days_weather_summary_cell, parent, false);
        return new WeatherEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        String currentDay = getOtherDay(data.get(position).getDate());
        WeatherEventViewHolder holder = (WeatherEventViewHolder) viewHolder;
        holder.itemView.setClickable(true);
        holder.nextDaysCurrentDay.setText(currentDay);
        holder.nextDaysMaxTemp.setText(String.valueOf(data.get(position).getTempJsonResponse().getMax() + " \u2103"));
        holder.nextDaysMinTemp.setText(String.valueOf(data.get(position).getTempJsonResponse().getMin() + " \u2103"));
        Picasso.with(context)
                .load(weatherIconMapper.getImageUri(data.get(position).getWeatherJsonResponse().get(0).getIcon()))
                .noFade()
                .into(holder.imageView);

    }

//    @Override
//    public void onBindViewHolder(WeatherEventViewHolder holder, int position) {
//        String currentDay = getOtherDay(data.get(position).getDate());
//        holder.nextDaysCurrentDay.setText(currentDay);
//        holder.nextDaysMaxTemp.setText(String.valueOf(data.get(position).getTempJsonResponse().getMax() + " \u2103"));
//        holder.nextDaysMinTemp.setText(String.valueOf(data.get(position).getTempJsonResponse().getMin() + " \u2103"));
//        Picasso.with(context)
//                .load(weatherIconMapper.getImageUri(data.get(position).getWeatherJsonResponse().get(0).getIcon()))
//                .noFade()
//                .into(holder.imageView);
//    }

    public static String getOtherDay(long value) {
        String day = "";
        Date date = new Date(value * 1000);
        day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        return day;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setWeatherData(List<TempListJsonResponse> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public TempListJsonResponse getWeatherItem(int position) {
        return data.get(position);
    }

    public class WeatherEventViewHolder extends BaseRecyclerAdapter.ViewHolder {

        @Bind(R.id.next_days_current_day)
        TextView nextDaysCurrentDay;
        @Bind(R.id.next_days_weather_image)
        ImageView imageView;
        @Bind(R.id.next_days_weather_max_temp)
        TextView nextDaysMaxTemp;
        @Bind(R.id.next_days_weather_min_temp)
        TextView nextDaysMinTemp;

        public WeatherEventViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
