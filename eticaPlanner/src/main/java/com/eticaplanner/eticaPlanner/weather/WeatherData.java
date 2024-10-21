package com.eticaplanner.eticaPlanner.weather;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="weather")
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weather_id")
    private int weatherId;

    @Column(name = "location")
    private String location;

    @Column(name = "temperature")
    private String temperature;

    @Column(name = "precipitation")
    private String precipitation;
}
