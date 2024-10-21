package com.eticaplanner.eticaPlanner.weather;

import com.eticaplanner.eticaPlanner.PlannerPage.controller.ApiComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.config.annotation.web.configurers.ServletApiConfigurer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import static java.lang.Integer.parseInt;

@EnableScheduling
@Service
public class WeatherService {

    private ApiComponent weather_key;
    private final WeatherDataRepository weatherDataRepository;
    private final RestTemplate restTemplate;
    private final HashMap<String, String[]> locationCoordinates;

    @Autowired
    public WeatherService(WeatherDataRepository weatherDataRepository, ApiComponent weather_key, RestTemplateBuilder restTemplateBuilder){
        this.weatherDataRepository = weatherDataRepository;
        this.weather_key = weather_key;
        this.restTemplate = restTemplateBuilder.build();
        this.locationCoordinates = new HashMap<>();
        initializeLocationCoordinates();
    }

    private void initializeLocationCoordinates() {
        locationCoordinates.put("서울", new String[]{"60", "127"});    // 서울
        locationCoordinates.put("부산", new String[]{"98", "76"});     // 부산
        locationCoordinates.put("대구", new String[]{"89", "90"});     // 대구
        locationCoordinates.put("인천", new String[]{"55", "124"});  // 인천
        locationCoordinates.put("광주", new String[]{"58", "74"});   // 광주
        locationCoordinates.put("대전", new String[]{"67", "100"});  // 대전
        locationCoordinates.put("울산", new String[]{"102", "84"});    // 울산
        locationCoordinates.put("경기", new String[]{"60", "120"});  // 경기
        locationCoordinates.put("강원", new String[]{"73", "134"});  // 강원
        locationCoordinates.put("충북", new String[]{"69", "107"}); // 충북
        locationCoordinates.put("충남", new String[]{"68", "100"}); // 충남
        locationCoordinates.put("전남", new String[]{"51", "67"});   // 전남
        locationCoordinates.put("경북", new String[]{"87", "106"});// 경북
        locationCoordinates.put("경남", new String[]{"91", "77"}); // 경남
        locationCoordinates.put("제주", new String[]{"52", "38"});      // 제주

    }


    public void fetchAndSaveWeather(String location) {
        System.out.println("fetchAndSaveWeather");
        LocalDate now = LocalDate.now();
        String today = now.toString().replace("-", "");

        LocalDateTime time = LocalDateTime.now();

        if (time.getMinute() < 30) {
            time = time.minusHours(1);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH00");
        String currentTime = time.format(formatter);

        String[] coordinates = locationCoordinates.get(location);

        String nx = coordinates[0];
        String ny = coordinates[1];
        String urlKey = weather_key.weather_apikey();
        String url = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?serviceKey="+URLEncoder.encode(urlKey, StandardCharsets.UTF_8)+"&dataType=JSON&pageNo=1&numOfRows=10&base_date="+today+"&base_time="+currentTime+"&nx="+nx+"&ny="+ny;
        System.out.println(url);
        try{
            URI uri = new URI(url);
            WeatherApiResponse response = restTemplate.getForObject(uri, WeatherApiResponse.class);
            System.out.println(response);
        if (response != null && response.getResponse().getBody().getItems() != null &&
            !response.getResponse().getBody().getItems().getItem().isEmpty()) {

            List<WeatherApiResponse.Item> items = response.getResponse().getBody().getItems().getItem();

            WeatherData weatherData = new WeatherData();
            weatherData.setLocation(location);

            for (WeatherApiResponse.Item item : items) {
                if ("TMP".equals(item.getCategory())) {
                    weatherData.setTemperature(item.getFcstValue());
                } else if ("POP".equals(item.getCategory())) {
                    weatherData.setPrecipitation(item.getFcstValue());
                }
            }

            // TMP 또는 POP 중 하나라도 설정된 경우에만 저장
            if (weatherData.getTemperature() != null || weatherData.getPrecipitation() != null) {
                weatherDataRepository.save(weatherData);
            } else {
                WeatherData existingWeatherData = weatherDataRepository.findByLocation(location);
            }
        }
        } catch(Exception e){
            WeatherData existingWeatherData = weatherDataRepository.findByLocation(location);
        }
    }

    public void fetchAndSaveWeatherAllLocations() {
        System.out.println("fetchAndSaveWeatherAllLocations");
        for (String location : locationCoordinates.keySet()) {
            try {
                fetchAndSaveWeather(location);
            } catch (Exception e) {
                System.err.println("Fail");
            }
        }
    }

    @Scheduled(cron = "0 20 * * * ?")
//    @Scheduled(fixedRate = 60000)
    public void scheduledWeatherUpdate() {
        System.out.println("scheduledWeatherUpdate");
        fetchAndSaveWeatherAllLocations();
    }


//    public static void main(String[] args) {
//        WeatherDataService weatherDataService = new WeatherDataService();
//        List<WeatherData> recentWeatherData = weatherDataService.getRecentWeatherData();
//    }
}
