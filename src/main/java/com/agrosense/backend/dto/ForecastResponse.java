package com.agrosense.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class ForecastResponse {

    @JsonProperty("list")
    private List<ForecastItem> list;

    @JsonProperty("city")
    private City city;

    @Data
    public static class ForecastItem {
        @JsonProperty("dt")
        private long dt; // Unix timestamp

        @JsonProperty("main")
        private Main main;

        @JsonProperty("weather")
        private List<Weather> weather;

        @JsonProperty("wind")
        private Wind wind;

        @JsonProperty("dt_txt")
        private String dtTxt; // Date/time in text format
    }

    @Data
    public static class Main {
        private double temp;
        @JsonProperty("temp_min")
        private double tempMin;
        @JsonProperty("temp_max")
        private double tempMax;
        private double humidity;
        @JsonProperty("feels_like")
        private double feelsLike;
    }

    @Data
    public static class Weather {
        private String main;
        private String description;
        private String icon;
    }

    @Data
    public static class Wind {
        private double speed;
        private double deg;
    }

    @Data
    public static class City {
        private String name;
        private String country;
    }
}
