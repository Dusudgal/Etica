package com.eticaplanner.eticaPlanner.Admin.entity;

import jakarta.persistence.*;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="add_admin_travel")
@Entity
public class TravelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="travel_no")
    private int travelNo;

    @Column(name="travel_name", nullable = false)
    private String travelName;

    @Column(name="travel_text", nullable = false)
    private String travelText;

    @Column(name="travel_xcd", nullable = false)
    private String travelXcd;

    @Column(name="travel_ycd", nullable = false)
    private String travelYcd;

    public TravelEntity(TravelDTO dto){
        this.travelName = dto.getTravel_name();
        this.travelText = dto.getTravel_context();
        this.travelXcd = dto.getTravel_X_marker();
        this.travelYcd = dto.getTravel_Y_marker();
    }
}
