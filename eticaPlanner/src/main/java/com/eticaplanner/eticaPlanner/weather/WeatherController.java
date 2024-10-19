package com.eticaplanner.eticaPlanner.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WeatherController {
    private final WeatherDataService weatherDataService;

    @Autowired
    public WeatherController(WeatherDataService weatherDataService) {
        this.weatherDataService = weatherDataService;
    }

    @GetMapping("/weather")
    public String weatherView(Model model) {
        System.out.println("[weatherView]");
        List<WeatherData> recentWeatherData = weatherDataService.getRecentWeatherData();
        model.addAttribute("weatherData", recentWeatherData);
        return "template/weather";  // JSP 파일의 경로
    }


}
