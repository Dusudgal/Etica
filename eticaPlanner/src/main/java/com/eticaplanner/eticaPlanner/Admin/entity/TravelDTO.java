package com.eticaplanner.eticaPlanner.Admin.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TravelDTO {
    private String travel_name;
    private String travel_context;
    private String travel_X_marker;
    private String travel_Y_marker;
}
