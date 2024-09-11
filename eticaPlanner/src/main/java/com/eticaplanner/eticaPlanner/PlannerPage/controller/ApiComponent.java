package com.eticaplanner.eticaPlanner.PlannerPage.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class ApiComponent {

    @Value("${api_keys.tour_key}")
    private String apikey;

    public String apikey(){
        return apikey;
    }
}