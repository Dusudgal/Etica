package com.eticaplanner.eticaPlanner.weather;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Integer> {
    public WeatherData findByWeatherId(int weather_id);

    List<WeatherData> findTop15ByOrderByWeatherIdDesc();

    public WeatherData findByLocation(String location);
}
