package com.eticaplanner.eticaPlanner;

import com.eticaplanner.eticaPlanner.noticeBoard.dto.NoticeResponseDto;
import com.eticaplanner.eticaPlanner.noticeBoard.service.NoticeService;
import com.eticaplanner.eticaPlanner.weather.WeatherData;
import com.eticaplanner.eticaPlanner.weather.WeatherDataService;
import com.eticaplanner.eticaPlanner.weather.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    public WeatherDataService weatherDataService;
    public NoticeService noticeService;
    @Autowired
    public MainController(WeatherDataService weatherDataService , NoticeService noticeService){
        this.weatherDataService = weatherDataService;
        this.noticeService = noticeService;
    }

    @GetMapping({"","/"})
    public String index(Model model){
        System.out.println("[MainController] index()");
        List<WeatherData> recentWeatherData = weatherDataService.getRecentWeatherData();
        model.addAttribute("weatherData", recentWeatherData);
        // 최신 공지사항 가져오기
        Optional<NoticeResponseDto> latestNotice = noticeService.getLatestNotice();
        // 공지사항이 있을 경우 모델에 추가, 없을 경우 공지사항을 모델에 추가하지 않음
        latestNotice.ifPresent(notice -> model.addAttribute("notice", notice));
        return "index";
    }

}
