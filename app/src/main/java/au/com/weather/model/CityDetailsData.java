package au.com.weather.model;

import au.com.weather.json.CityDetailsJsonResponse;

public class CityDetailsData extends CityDetailsJsonResponse {
    Long id;
    String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
