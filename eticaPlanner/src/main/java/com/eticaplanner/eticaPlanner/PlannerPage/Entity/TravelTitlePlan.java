package com.eticaplanner.eticaPlanner.PlannerPage.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Travel_Title_Plan")
public class TravelTitlePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_no")
    private Integer planNo;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "plan_title", nullable = false, length = 100)
    private String planTitle;

    @Column(name = "travel_start_day", nullable = false)
    private LocalDate travelStartDay;

    @Column(name = "travel_end_day", nullable = false)
    private LocalDate travelEndDay;

    @Column(name = "plan_agree", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean planAgree = false;

    @Column(name = "plan_create_day")
    private LocalDate planCreateDay;

    @Column(name = "plan_modify_day")
    private LocalDate planModifyDay;

    @PrePersist
    protected void onCreate() {
        if (planCreateDay == null) {
            planCreateDay = LocalDate.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        planModifyDay = LocalDate.now();
    }

}
