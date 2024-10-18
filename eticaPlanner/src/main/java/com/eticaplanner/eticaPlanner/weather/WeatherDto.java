package com.eticaplanner.eticaPlanner.weather;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherDto {
    private int weather_id;
    private String location;
    private String temperature;
    private String precipitation;
}
