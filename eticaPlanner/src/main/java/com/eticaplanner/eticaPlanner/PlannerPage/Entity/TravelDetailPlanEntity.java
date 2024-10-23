package com.eticaplanner.eticaPlanner.PlannerPage.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Travel_Detail_Plan")
public class TravelDetailPlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_plan_no")
    private Integer detailPlanNo;

    @Column(name = "plan_no", nullable = false)
    private Integer planNo;

    @Column(name = "user_id", nullable = false, length = 100)
    private String userId;

    @Column(name = "plan_duration_days", nullable = false)
    private Integer planDurationDays;

    @Column(name = "tourist_attraction_name", nullable = false, length = 255)
    private String touristAttractionName;

    @Column(name = "tourist_attraction_text", columnDefinition = "TEXT")
    private String touristAttractionText;

    @Column(name = "tourist_attraction_address", length = 255)
    private String touristAttractionAddress;

    @Column(name = "tourist_attraction_photo_address", length = 255)
    private String touristAttractionPhotoAddress;

    @Column(name = "tourist_attraction_mapx")
    private String planTouristMapx;

    @Column(name = "tourist_attraction_mapy")
    private String planTouristMapy;
}
