package com.eticaplanner.eticaPlanner.PlannerPage.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Travel_Title_Plan")
public class TravelTitlePlanEntity {

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

    @CreationTimestamp
    @Column(name = "plan_create_day")
    private LocalDate planCreateDay;

    @UpdateTimestamp
    @Column(name = "plan_modify_day")
    private LocalDate planModifyDay;
}
