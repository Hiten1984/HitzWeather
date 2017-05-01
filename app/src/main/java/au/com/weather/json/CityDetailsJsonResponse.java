package au.com.weather.json;


import com.google.gson.annotations.SerializedName;

public class CityDetailsJsonResponse {

    @SerializedName("id")
    Long id;

    @SerializedName("name")
    String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
