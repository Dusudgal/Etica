package com.eticaplanner.eticaPlanner;

import com.eticaplanner.eticaPlanner.weather.WeatherData;
import com.eticaplanner.eticaPlanner.weather.WeatherDataService;
import com.eticaplanner.eticaPlanner.weather.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    public WeatherDataService weatherDataService;

    @Autowired
    public MainController(WeatherDataService weatherDataService){
        this.weatherDataService = weatherDataService;
    }

    @GetMapping({"","/"})
    public String index(Model model){
        System.out.println("[MainController] index()");
        List<WeatherData> recentWeatherData = weatherDataService.getRecentWeatherData();
        model.addAttribute("weatherData", recentWeatherData);
        return "index";
    }

}
