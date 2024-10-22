package com.eticaplanner.eticaPlanner.PlannerPage.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
public class ApiComponent {

    @Value("${api_keys.tour_key}")
    private String apikey;

    @Value("${api_keys.kakao_javascriptkey}")
    private String kakao_scriptkey;

    @Value("${weather.api.key}")
    private String weather_key;

    public String map_apikey() { return kakao_scriptkey; }

    public String tour_apikey(){
        return apikey;
    }

    public String weather_apikey() { return weather_key; }
}